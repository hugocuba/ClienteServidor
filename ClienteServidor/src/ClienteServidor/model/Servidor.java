package ClienteServidor.model;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor implements Runnable {

    private Integer porta;
    private ServerSocket servidor = null;
    private List<PrintStream> clientes;

    public Servidor(Integer porta) {
        this.porta = porta;
    }
    
    @Override
    public void run() {
        try {
            servidor = new ServerSocket(porta);

            if (servidor != null) {
                System.out.println("Porta " + porta + " aberta");

                while(!Thread.currentThread().isInterrupted()){
                    System.out.println("Esperando...");
                    Socket cliente = servidor.accept();
                    System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
                }
                
                System.out.println("Finalizado");
                
                /*Scanner entrada = new Scanner(cliente.getInputStream());

                while (entrada.hasNextLine()) {
                    System.out.println("O cliente digitou: " + entrada.nextLine());
                }

                entrada.close();
                servidor.close();*/
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}