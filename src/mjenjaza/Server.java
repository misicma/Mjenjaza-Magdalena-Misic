/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjenjaza;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap; //sluzi za pamcenje korisnika
//po principu dictionary iz python-a - key/value
//ConcurrentHashMap jer je pogodna za prijavu vise korisnika odjednom

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
        List<Integer> cards = new ArrayList<>();
        Random random = new Random(); //jedan Random objekat za sve
        private static ConcurrentHashMap<String, Korisnik> korisnici = new ConcurrentHashMap<>();
        
        
        System.out.println("Server is starting and waiting for a client...");
        
        try (ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader reader = new BufferedReader(isr))    {
            
            
            System.out.println("Client connected successfully!");
            
            //Read the name sent by the client
            String receivedName = reader.readLine();
            System.out.println("Received name from client: "+receivedName);
            
            //Generisi 7 karata
            System.out.println("\n----Generisanje karata-----\n");
            for (int i=0; i<=7 ;i++){
                int num_of_doubles = random.nextInt(100) + 1;
                cards.add(num_of_doubles);
                System.out.println("You will get card - "+num_of_doubles);
            }
            
            System.out.println("All cards are:");
            for (int i = 0; i < cards.size(); i++) {
                System.out.println("Card: " + (i+1) + ": " + cards.get(i));
            }
            
            
        }   catch(IOException e) {
            System.out.println("Server error: " +e.getMessage());
        
        }      
    }
}

//kada je jednom povezano on cita ime koristeci BUfferedReader
