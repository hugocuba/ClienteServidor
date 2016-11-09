package ClienteServidor.model;

import ClienteServidor.view.ServidorJFrame;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor implements Runnable {

    private Integer porta;
    private ServerSocket servidor = null;
    private List<Socket> clients = new ArrayList<>();

    public Servidor(Integer porta) {
        this.porta = porta;
    }
    
    public void removeCliente(Socket cliente){
        this.clients.remove(cliente);
    }

    @Override
    public void run() {
        try {
            servidor = new ServerSocket(porta);

            if (servidor != null) {
                ServidorJFrame.escreveChat("Porta " + porta + " aberta\n");

                while (!Thread.currentThread().isInterrupted()) {
                    ServidorJFrame.escreveChat("Esperando por conexões...\n");
                    Socket cliente = servidor.accept();
                    ServidorJFrame.escreveChat("---------------------------\n");
                    ServidorJFrame.escreveChat("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress() + "\n");
                   
                    this.clients.add(cliente);
                    ServidorJFrame.escreveChat(clients.size() + " clientes conectados\n");
                    
                    Runnable tc = () -> {
                        try {
                            Scanner s = new Scanner(cliente.getInputStream());
                            while (s.hasNextLine()) {
                                String msg = s.nextLine();
                                ServidorJFrame.escreveChat(cliente.getInetAddress().getHostAddress() + " - " + cliente.hashCode() + " enviou uma mensagem\n");
                                for (Socket cli : this.clients) {
                                    PrintStream psm = new PrintStream(cli.getOutputStream());
                                    psm.println(cliente.getInetAddress().getHostAddress() + " - " + cliente.hashCode() + ": " + msg);
                                    
                                }
                            }
                            s.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    };

                    new Thread(tc).start();
                }

                ServidorJFrame.escreveChat("Finalizado\n");
                servidor.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
