/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClienteServidor.model;
/**
 *
 * @author a1502794kkk
 * 
 */
public class Main {
    public static void main(String[] args) {
        
        //Servidor servidor = new Servidor(12345);
        //Thread servidorT = new Thread(servidor);
        //servidorT.start();
        
        Cliente cliente = new Cliente("127.0.0.1", 12345);
        new Thread(cliente).start();
        
        //Cliente cliente2 = new Cliente("127.0.0.1", 2000);
        //new Thread(cliente).start();
        
    }
}
