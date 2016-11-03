package ClienteServidor.model;

import ClienteServidor.view.ClienteJFrame;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente implements Runnable {

    private String ip;
    private Integer porta;
    private Socket cliente = null;
    PrintStream saida;

    public Cliente(String ip, Integer porta) {
        this.ip = ip;
        this.porta = porta;
    }

    public Socket getCliente(){
        return this.cliente;
    }

    @Override
    public void run() {
        try {
            cliente = new Socket(ip, porta);

            if (cliente != null) {
                ClienteJFrame.escreveChat("Conectado\n\n");
                
                //Scanner enviado = new Scanner(msg.);
                Scanner recebido = new Scanner(cliente.getInputStream());

                saida = new PrintStream(cliente.getOutputStream());
                
                Runnable entradaTask = () -> {
                    while (recebido.hasNextLine()) {
                         ClienteJFrame.escreveChat(recebido.nextLine() + "\n");
                    }
                };

                //Runnable saidaTask = () -> {
                //    while (enviado.hasNextLine()) {
                //        saida.println(enviado.nextLine());
                //    }
                //};
                
                //new Thread(saidaTask).start();
                new Thread(entradaTask).start();

                //saida.close();
                //enviado.close();
                //recebido.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviar(String msg){
        saida.println(msg);
    }
}
