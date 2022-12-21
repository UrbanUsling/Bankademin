package org.example.View;


import org.example.Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminPanel extends JPanel {

    private List<String> allTransactions = Controller.getAllTransactions();

    private JPanel northPanel = new JPanel();
    private JPanel leftNorthPanel = new JPanel();
    private JLabel leftNorthLabel1 = new JLabel("   Antal kunder: " + (Controller.getNumberOfCustomers()));
    private JLabel leftNorthLabel2 = new JLabel("   Antal transaktioner: " + (Controller.getNumberOfTransactions()));
    private JLabel leftNorthLabel3 = new JLabel("   Totalt kapital: " + Controller.getTotalCapital() + " SEK");

    private JPanel southPanel = new JPanel();

    private JTextArea textArea = new JTextArea(25, 40);
    private JScrollPane scrollpane = new JScrollPane(textArea);

    public AdminPanel() {
        //main
        this.setLayout(new GridLayout(2, 0));

        this.add(northPanel);
        this.add(southPanel);

        //NorthPanel
        northPanel.setLayout(new GridLayout(1, 2));
        northPanel.add(leftNorthPanel);

        //LeftNorthPanel
        leftNorthPanel.setLayout(new GridLayout(3, 2));
        leftNorthPanel.add(leftNorthLabel1);
        leftNorthPanel.add(leftNorthLabel2);
        leftNorthPanel.add(leftNorthLabel3);


        //southpanel
        southPanel.setLayout(new GridLayout(1,0));
        southPanel.add(scrollpane);

        textArea.setText("Senaste transaktioner:\n\n ");
        for (String line : allTransactions) {
            textArea.append(line + "\n");

        }

    }
}