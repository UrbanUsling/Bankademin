package org.example.View;

import org.example.Model.Customer;
import org.example.Model.User;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    private JTextArea textArea = new JTextArea();
    private static JTextArea updatedHome = new JTextArea(20, 50);
    private static boolean changedHome= false;
    private JLabel message = new JLabel("Skriv nytt meddelande under");
    private User currentCustomer;

    public HomePanel(User currentCustomer){
        //Ändrat från customer till user. Både admin och customer är user
        this.currentCustomer = currentCustomer;
        if(!changedHome) {
            textArea.setFont(new Font("Sans-serif", Font.BOLD, 20));
            textArea.setText("\nVälkommen till Bankademins tillfälliga internettjänst.\n" +
                    "Tyvärr ligger våran ordinarie sida nere just nu. Vissa tjänster är inte tillgängliga. Vi jobbar på felet");
        }else{
            textArea=updatedHome;
        }
        textArea.setFont(new Font("Sans-serif", Font.BOLD, 18));
        textArea.setEditable(false);
        this.add(textArea);

        this.setBackground(Color.WHITE);
        revalidate();
        repaint();
    }
    public HomePanel(){
        updatedHome.setFont(new Font("Sans-serif", Font.BOLD, 18));
        updatedHome.setEditable(true);
        this.add(message);
        this.add(updatedHome, BorderLayout.CENTER);
        this.setBackground(Color.WHITE);
        setTextArea(updatedHome);
        changedHome = !updatedHome.getText().equals("");
        this.revalidate();
        this.repaint();
    }

    private void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }
}
