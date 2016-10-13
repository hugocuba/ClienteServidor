package ClienteServidor.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente implements Runnable {

    private String ip;
    private Integer porta;
    Socket cliente = null;

    public Cliente(String ip, Integer porta) {
        this.ip = ip;
        this.porta = porta;
    }

    @Override
    public void run() {
        try {
            cliente = new Socket(ip, porta);

            if (cliente != null) {
                System.out.println("O cliente se conectou ao servidor!");
                
                Scanner enviado = new Scanner(System.in);
                Scanner recebido = new Scanner(cliente.getInputStream());

                PrintStream saida = new PrintStream(cliente.getOutputStream());
                
                Runnable entradaTask = () -> {
                    while (recebido.hasNextLine()) {
                        System.out.println(recebido.nextLine());
                    }
                };

                Runnable saidaTask = () -> {
                    while (enviado.hasNextLine()) {
                        saida.println(enviado.nextLine());
                    }
                };
                
                new Thread(saidaTask).start();
                new Thread(entradaTask).start();

                //saida.close();
                //teclado.close();
                //cliente.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
