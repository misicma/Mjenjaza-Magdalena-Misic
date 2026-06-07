/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mjenjaza;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author magdalena
 */
public class Mjenjaza {

    /**
     * @param args the command line arguments
     */
        // Promjenljive koje će nam trebati u cijelom programu
        private static String name;
        private static JPanel panelDoubles;
        private static JPanel panelNeeded;
        private static java.util.List<JCheckBox> doublesList = new ArrayList<>();
        private static java.util.List<JCheckBox> neededList = new ArrayList<>();
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Displays a pop-up dialog box to capture the user input
        name = JOptionPane.showInputDialog(null, "Please enter your name:" );
        
        //Displays a message box with the captured name
        if (name != null && !name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hello, "+name+"!");
        } else {
            JOptionPane.showMessageDialog(null, "You need to enter your name again!");
            System.exit(0);
        }
        
        String correctName = name.trim();
        
        String answerFromServer = "";
        
        try{
            Socket socket;
            socket = new Socket("localhost",5000);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            writer.println(correctName);
            answerFromServer = reader.readLine();
            
            socket.close();
            
        } catch(Exception e) {
            answerFromServer = "Error: "+ e.getMessage();
        }
        
        // --- 3.GLAVNI PROZOR ---
        JFrame frame = new JFrame("Exchange -" +correctName);
        frame.setSize(800,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        textArea.append("=== Welcome to EXCHANGE ===\n");
        textArea.append("User: " + correctName + "\n");
        textArea.append("============================\n\n");
        textArea.append("Answer From Server:\n");
        textArea.append(answerFromServer + "\n");
        
        // --- PANELI ZA SLICICE
        
        //rows,cols,hgap, vgap - adding pixel gaps horizontally/verticaly
        JPanel middlePanel = new JPanel(new GridLayout(1,2,10,10));
        
        //Panel za duplikate
        panelDoubles = new JPanel();
        panelDoubles.setLayout(new GridLayout(0,4,5,5));
        panelDoubles.setBorder(BorderFactory.createTitledBorder("DOUBLES"));
        panelDoubles.setBackground(new Color(220,255,220));
        
        //Panel za potrebne 
        panelNeeded = new JPanel();
        panelNeeded.setLayout(new GridLayout(0,4,5,5));
        panelNeeded.setBorder(BorderFactory.createTitledBorder("NEEDED"));
        panelNeeded.setBackground(new Color(255,229,220));
        
        //PARSIRANJE ODGOVORA I DODAVANJE CHECKBOX-OVA
        if(answerFromServer.contains("DOUBLES:") && answerFromServer.contains("NEEDED:")) {
            try{
                String[] parts = answerFromServer.split(";"); //dobijamo string
                String doublesPart = parts[0].replace("DOUBLES:",""); //zamjenjujemo "DOUBLES" sa praznim stringom
                String neededPart = parts[1].replace("NEEDED:","");
                
                //Metoda koja uzima odredjeni dio stringa i vraca ga kao potpuno novi objekat
                doublesPart = doublesPart.substring(1,doublesPart.length()-1);
                neededPart = neededPart.substring(1,neededPart.length()-1);
                
                //Dodaje text na kraj
                textArea.append("\n----SEPARATED----\n");
                textArea.append("Your doubles: "+doublesPart+"\n");
                textArea.append("Your needeed:"+neededPart+"\n");
                
                // Dodavanje duplikata
                if (!doublesPart.isEmpty()) {
                    for (String broj : doublesPart.split(", ")) {
                        JCheckBox cb = new JCheckBox(broj);
                        panelDoubles.add(cb);
                        doublesList.add(cb);
                        log.append("Dodat duplikat: " + broj + "\n");
                    }
                }
                
                // Dodavanje potrebnih
                if (!neededPart.isEmpty()) {
                    for (String broj : neededPart.split(", ")) {
                        JCheckBox cb = new JCheckBox(broj);
                        panelNeeded.add(cb);
                        neededList.add(cb);
                        log.append("Dodata potrebna: " + broj + "\n");
                    }
                }
                
                log.append("ALL DOUBLES: "+doublesList.size()+"\n");
                log.append("ALL NEEDED: "+neededList.size()+"\n");
                
            } catch(Exception e){
                log.append("\nError in parsing:"+e.getMessage()+"\n");
            }
        } else {
            log.append("\n(Answer is in incorrect form!)\n");
        }
        
        middlePanel.add(new JScrollPane(panelDoubles));
        middlePanel.add(new JScrollPane(panelNeeded));
        
        //Buttons - dugmadi
        //FlowLayout - components line by line
        JPanel bottomPanel = new JPanel(new FlowLayout()); 
        JButton btnExchange = new JButton("Possible exchanges");
        JButton btnDelete = new JButton("Delete selected!");
        bottomPanel.add(btnExchange);
        bottomPanel.add(btnDelete);
                
        btnExchange.addActionListener(e -> {
            textArea.append("\n>>> Button 'Possible exchanges' is clicked<<<\n"); 
            log.append("\n---I AM LOOKING FOR POSSIBLE EXCHANGES---\n");
            
            try{
                Socket socket = new Socket("localhost",5000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                out.println("GET_USERS");
                String answer = in.readLine();
                socket.close();
                
                if(answer!=null && answer.startsWith("USERS_LIST:")) {
                    String names = answer.replace("USERS_LIST", "");
                    String[] listOfNames = names.split(",");
                }
            }
        });
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
    
}
