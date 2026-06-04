/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjenjaza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author magdalena
 */
public class Server {

    public Server(int port) {
    }
    public static void main(String[] args){
        int port = 5000; //Open port
        
        System.out.println("Server is starting and waiting for a client...");
        
        try (ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader reader = new BufferedReader(isr))    {
            
            
            System.out.println("Client connected successfully!");
            
            //Read the name sent by the client
            String receivedName = reader.readLine();
            System.out.println("Received name from client: "+receivedName);
            
        }   catch(IOException e) {
            System.out.println("Server error: "+e.getMessage());
        }      
    }
}

//kada je jednom povezano on cita ime koristeci BUfferedReader
