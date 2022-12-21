package org.example.View;

import org.example.Controller.Controller;
import org.example.Model.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyAccountsPanel extends JPanel {
    private Customer currentCustomer;
    private JLabel customerName = new JLabel();
    private JButton accountName = new JButton();
    private JLabel balance = new JLabel();
    private JTextArea transactions = new JTextArea(25, 40);
    private JScrollPane scrollpane = new JScrollPane(transactions);


    public MyAccountsPanel(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;

        customerName.setText("Kund: " + currentCustomer.getName());
        customerName.setFont(new Font("Sans-serif", Font.BOLD, 25));

        accountName.setText("Konto: " + currentCustomer.getAccount().getAccountNumber());
        accountName.setFont(new Font("Sans-serif", Font.BOLD, 25));
        accountName.setBackground(Color.decode("#C0DEFF"));
        accountName.setFocusable(false);

        balance.setText("Saldo: " + currentCustomer.getAccount().getBalance());
        balance.setFont(new Font("Sans-serif", Font.BOLD, 25));

        this.add(customerName);
        this.add(Box.createHorizontalStrut(15));
        this.add(accountName);
        this.add(Box.createHorizontalStrut(15));
        this.add(balance);

        accountName.addActionListener(listener -> {
            List<String> allTrans = Controller.findMyTransactions(currentCustomer);
            String content = getTransLines(allTrans);

            transactions.setFont(new Font("Sans-serif", Font.BOLD, 22));
            transactions.setText(content);
            transactions.setEditable(false);
            transactions.setBorder(null);
            this.add(scrollpane, BorderLayout.SOUTH);
            this.revalidate();
            this.repaint();
        });

    }

    private String getTransLines(List<String> allTrans) {
        StringBuilder lines = new StringBuilder();
        for(String line : allTrans) {
            lines.append(line).append("\n");
        }
        return lines.toString();
    }

}