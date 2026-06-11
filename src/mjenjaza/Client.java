/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjenjaza;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;

/**
 *
 * @author magdalena
 */
public class Client {
    public static void main(String [] args) {
        String serverAdress = "localhost"; //127.0.0.1 - localhost ip adress
        int port = 5000; //TCP port
        String nameToSend = "Magdalena";
        
        System.out.println("Attemting to connect to the server...");
        
        //Server adress ili host
        try(Socket socket = new Socket(serverAdress, port);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            
            
        System.out.println("Connected! Sending name: "+nameToSend);
        
        //Send the name followed by a newline character
        writer.println(nameToSend);
        
        System.out.println("Name sent successfully");
        
        } catch (IOException e) {
            System.err.println("Client error: "+e.getMessage());
    }
}
}


//https://chat.deepseek.com/share/eg6wuxc79b7dsqc369