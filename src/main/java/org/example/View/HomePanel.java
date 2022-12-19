package org.example.View;

import org.example.Model.Customer;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    private JTextArea textArea = new JTextArea();

    private Customer currentCustomer;

    private boolean adminMode;

    public HomePanel(Customer currentCustomer, boolean adminMode){
        this.currentCustomer = currentCustomer;
        this.adminMode = adminMode;
        loadContent();
    }

    public void loadContent() {
        String content = adminMode ? "\n Du är inloggad som Admin" : "\n" + currentCustomer.getName() + ", Välkommen till Bankademins tillfälliga internettjänst.\n" +
                "Tyvärr ligger våran ordinarie sida nere just nu. Vissa tjänster är inte tillgängliga. Vi jobbar på felet";

        textArea.setText(content);
        textArea.setFont(new Font("Sans-serif", Font.BOLD, 20));
        textArea.setEditable(false);
        this.setBackground(Color.WHITE);
        this.add(textArea);
    }

}
