package org.example.View;

import org.example.Controller.Controller;
import org.example.Model.Customer;
import org.example.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.NoSuchElementException;

public class TransferPanel extends JPanel {

    private Customer currentCustomer;
    private JButton sendAccBtn = new JButton("Överföring andra konton");
    private JButton payBillsBtn = new JButton("Betalning PG/BG");
    private JLabel accountNumberLabel = new JLabel("Konto: ");
    private JTextField accountNumber = new JTextField("                     ");
    private JLabel amountLabel = new JLabel("Belopp: ");
    private JTextField amount = new JTextField("                     ");
    private JRadioButton bankGiroRadio = new JRadioButton("Bankgiro", false);
    private JRadioButton plusGiroRadio = new JRadioButton("Plusgiro", false);
    private JButton sendBtn = new JButton("Skicka");
    private String valBgPgChoice;
    private boolean isbgpg = false;

    public TransferPanel(User currentCustomer) {

        this.currentCustomer = (Customer) currentCustomer;
        this.setLayout(new GridBagLayout());

        System.out.println("CUSTOMER: " + currentCustomer);

        sendAccBtn.setFont(new Font("Sans-serif", Font.BOLD, 25));
        payBillsBtn.setFont(new Font("Sans-serif", Font.BOLD, 25));

        sendAccBtn.setBackground(Color.decode("#C0DEFF"));
        payBillsBtn.setBackground(Color.decode("#C0DEFF"));

        sendBtn.setFont(new Font("Sans-serif", Font.BOLD, 25));
        sendBtn.setBackground(Color.decode("#C0DEFF"));

        this.add(sendAccBtn);
        this.add(Box.createHorizontalStrut(15));
        this.add(payBillsBtn);

        addMouseListeners();
        addEventListeners();
    }
    public void setTransferPanel(){
        accountNumber.setFont(new Font("Sans-serif", Font.BOLD, 25));
        accountNumberLabel.setFont(new Font("Sans-serif", Font.BOLD, 25));
        this.add(accountNumberLabel);
        this.add(accountNumber);
        this.add(Box.createHorizontalStrut(15));

        amount.setFont(new Font("Sans-serif", Font.BOLD, 25));
        amountLabel.setFont(new Font("Sans-serif", Font.BOLD, 25));
        this.add(amountLabel);
        this.add(amount);
        this.add(Box.createHorizontalStrut(15));

        this.remove(sendAccBtn);
        this.remove(payBillsBtn);
    }
    private void addMouseListeners() {
        accountNumber.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                accountNumber.setText("");
            }
        });

        amount.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                amount.setText("");
            }
        });
    }

    private void addEventListeners() {
        sendAccBtn.addActionListener(event -> {
            setTransferPanel();
            sendBtn.setFont(new Font("Sans-serif", Font.BOLD, 25));
            this.add(sendBtn);
            this.revalidate();
            this.repaint();
        });

        sendBtn.addActionListener(event -> {
            String accountFormatted = accountNumber.getText().trim();
            String amountFormatted = amount.getText().trim();

            if(accountFormatted.isBlank() || amountFormatted.isBlank()) {
                JOptionPane.showMessageDialog(null, "Du måste fylla i båda fälten!");
            } else if (accountFormatted.equalsIgnoreCase(currentCustomer.getAccount().getAccountNumber())) {
                JOptionPane.showMessageDialog(null,"Du kan inte överföra pengar till ditt egna konto!");
            } else if (amountFormatted.contains("-")){
                JOptionPane.showMessageDialog(null,"Ange giltigt belopp! Inte minus");
            } else if (!(Controller.isDouble(amountFormatted))) {
                JOptionPane.showMessageDialog(null, "Bara siffror tack!");
            } else if(!accountFormatted.matches("[0-9]+")){
                JOptionPane.showMessageDialog(null, "Bara siffror tack!");
            }else{
                // Kör BG/PG metoden om BG/PG är valt annars metoden för interna konton
                if(isbgpg) {
                    handleTransferBGPG(accountFormatted, Double.parseDouble(amountFormatted));
                } else {
                    handleTransfer(accountFormatted, Double.parseDouble(amountFormatted));
                }
            }
        });

        bankGiroRadio.addActionListener(event -> {
            valBgPgChoice = "Bankgiro";
            sendBtn.setVisible(true);
            plusGiroRadio.setVisible(false);
            isbgpg = true;
            System.out.println("Bank Giro");
        });

        plusGiroRadio.addActionListener(event -> {
            valBgPgChoice = "Plusgiro";
            sendBtn.setVisible(true);
            bankGiroRadio.setVisible(false);
            isbgpg = true;
            System.out.println("Plus giro");
        });

        payBillsBtn.addActionListener(event -> {
            setTransferPanel();
            bankGiroRadio.setFont(new Font("Sans-serif", Font.BOLD, 25));
            plusGiroRadio.setFont(new Font("Sans-serif", Font.BOLD, 25));
            this.add(bankGiroRadio);
            this.add(plusGiroRadio);

            sendBtn.setFont(new Font("Sans-serif", Font.BOLD, 25));
            this.add(Box.createHorizontalStrut(15));
            this.add(sendBtn);
            sendBtn.setVisible(false);

            this.revalidate();
            this.repaint();

        });
    }

    private void handleTransfer(String account, double amount) {
        isbgpg = false;
        try {
            Customer receiver = Controller.getCustomerByAccountNr(account);
            boolean greatSuccess = Controller.transferToOtherAccount(amount, currentCustomer, receiver);
            if(greatSuccess) {
                JOptionPane.showMessageDialog(null, "Skickade " + amount + " Kr till Konto: " + account);
            } else {
                JOptionPane.showMessageDialog(null, "Du har ej tillräckligt med pengar på kontot, du har: " + currentCustomer.getAccount().getBalance() + " Kr");
            }
        } catch (NoSuchElementException e) {
            JOptionPane.showMessageDialog(null, "Konto nr finns ej");
        }

    }
    private void handleTransferBGPG(String account, double amount) {

            boolean greatSuccess = Controller.transferToBGPG(amount, currentCustomer, account, valBgPgChoice);
            if(greatSuccess) {
                JOptionPane.showMessageDialog(null, "Skickade " + amount + " Kr till " + valBgPgChoice + " konto: " + account);
            } else {
                JOptionPane.showMessageDialog(null, "Du har ej tillräckligt med pengar på kontot, du har: " + currentCustomer.getAccount().getBalance() + " Kr");
            }

    }


}