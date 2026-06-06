/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjenjaza;

import java.util.*;
//sluzi za pamcenje korisnika
//po principu dictionary iz python-a - key/value
//ConcurrentHashMap jer je pogodna za prijavu vise korisnika odjednom

import java.io.*;

import java.net.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 * @author magdalena
 */
public class Server {
    private static Map<String, User> users = new ConcurrentHashMap<>();
    
    public static void main(String[] args){
        int port = 5000; //Open port        
                
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is started on port "+port);
            
            while(true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> processClient(clientSocket)).start();   
            }
            
        }   catch(IOException e) {
            System.out.println("Server error: " +e.getMessage());
        }      
    }
    
    private static void processClient(Socket clientSocket) {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);    
            ) {
            
            //citanje imena korisnika
            String name = reader.readLine();
            System.out.println("Received name is: "+name);
         
            User user = users.get(name); //svrstava se u listu 
            
            if (user == null) {
                System.out.println("New User - generate...");
                List<Integer> doubles = generateDoubles(7);
                List<Integer> neededCards = generateNeededCards(6,doubles);
//                List<Integer> neededCards = null;
                
                
                user = new User(name, doubles, neededCards);
                users.put(name, user);
            } else {
                System.out.println("This user existed!");
            }
            
            writer.println("DOUBLES:"+user.doubles+";NEEDED:"+user.neededCards);
           
        } catch(IOException e) {
            System.out.println("Error in processing: "+e.getMessage());
        }
        
    }
    
    //Generisanje random duplih karata
    private static List<Integer> generateDoubles(int num) {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        while(list.size() < num) {
            int numCard = random.nextInt(99) + 1;
            if(!list.contains(numCard)) {
                list.add(numCard);
            }
        }
        return list;
    }
    
    //Generisanje potrebnih karata
    private static List<Integer> generateNeededCards(int num, List<Integer>doubles) {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        while(list.size() < num) {
            int cardNum = random.nextInt(99) + 1;
            if(!doubles.contains(cardNum)&& !list.contains(cardNum)) {
                list.add(cardNum);
            }
        }
        return list;
    }
  
}

class User {
    String name;
    List<Integer> doubles;
    List<Integer> neededCards;
    
    User(String name, List<Integer> doubles, List<Integer> neededCards) {
        this.name = name;
        this.doubles = doubles;
        this.neededCards = neededCards;
    }
}

//kada je jednom povezano on cita ime koristeci BUfferedReader
