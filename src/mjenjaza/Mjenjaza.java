/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mjenjaza;

import javax.swing.JOptionPane;

/**
 *
 * @author magdalena
 */
public class Mjenjaza {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Displays a pop-up dialog box to capture the user input
        String name = JOptionPane.showInputDialog(null, "Please enter your name:" );
        
        //Displays a message box with the captured name
        if (name != null && !name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hello, "+name+"!");
        }
        
    }
    
}
