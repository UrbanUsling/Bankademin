package org.example.View;



import org.example.Controller.Controller;
import org.example.Model.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.NoSuchElementException;

public class AdminPanel extends JPanel {
    private JLabel amountOfCustomers = new JLabel("Antal kunder: " + getAmountOfCustomersInBank());
    private JLabel amountOfTrans = new JLabel("Antal Transaktioner: " + getAllTrans().size());

    private JLabel searchCustomerLabel = new JLabel("Sök på kund id / konto id");
    private JLabel footerSearchResultCustomerInfo = new JLabel();

    private JLabel footerSearchResultAccountInfo = new JLabel();
    private JTextField searchField = new JTextField(15);
    private JPanel headerPanel = new JPanel();
    private JPanel bodyPanel = new JPanel();
    private JPanel footerPanel = new JPanel();

    private JButton transLogBtn = new JButton();
    public AdminPanel() {
        this.setLayout(new BorderLayout());

        // HEADER SECTION
        headerPanel.setPreferredSize(new Dimension(1000, 150));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.decode("#4B56D2")));
        headerPanel.setLayout(new BorderLayout());
        JLabel headerText = new JLabel("Översikt");
        headerText.setFont(new Font("Sans-serif", Font.BOLD, 25));
        amountOfCustomers.setFont(new Font("Sans-serif", Font.BOLD, 22));
        amountOfTrans.setFont(new Font("Sans-serif", Font.BOLD, 22));
        amountOfCustomers.setHorizontalAlignment(JLabel.CENTER);
        amountOfTrans.setHorizontalAlignment(JLabel.CENTER);
        headerText.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(headerText, BorderLayout.NORTH);
        headerPanel.add(amountOfCustomers, BorderLayout.CENTER);
        headerPanel.add(amountOfTrans, BorderLayout.SOUTH);


        // BODY
        bodyPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.decode("#4B56D2")));
        bodyPanel.setLayout(new GridBagLayout());
        searchCustomerLabel.setHorizontalAlignment(JLabel.CENTER);
        searchCustomerLabel.setFont(new Font("Sans-serif", Font.BOLD, 22));
        searchField.setFont(new Font("Sans-serif", Font.BOLD, 22));
        bodyPanel.add(searchCustomerLabel);
        bodyPanel.add(Box.createHorizontalStrut(15));
        bodyPanel.add(searchField);


        // FOOTER
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setPreferredSize(new Dimension(1000, 500));



        this.add(headerPanel, BorderLayout.NORTH);
        this.add(bodyPanel, BorderLayout.CENTER);
        this.add(footerPanel, BorderLayout.SOUTH);

        addListeners();
    }

    private void addListeners() {
        searchField.addActionListener(event -> {
            try {
                Customer match = Controller.getCustomerByIdOrAccountId(searchField.getText().trim());
                JLabel header = new JLabel("Klicka på kanppen längst ner för Trans. Historik");
                header.setFont(new Font("Sans-serif", Font.ITALIC, 15));

                footerSearchResultCustomerInfo.setText("ID: " + match.getId() + "      Namn: " + match.getName() + "      DOB: " + match.getDob());
                footerSearchResultCustomerInfo.setFont(new Font("Sans-serif", Font.BOLD, 22));
                footerSearchResultAccountInfo.setText("Account ID: " + match.getAccount().getAccountNumber() + "      Balance: " + match.getAccount().getBalance() + " SEK");
                footerSearchResultAccountInfo.setFont(new Font("Sans-serif", Font.BOLD, 22));
                footerSearchResultCustomerInfo.setHorizontalAlignment(JLabel.CENTER);
                footerSearchResultAccountInfo.setHorizontalAlignment(JLabel.CENTER);
                footerPanel.add(footerSearchResultCustomerInfo, BorderLayout.NORTH);
                footerPanel.add(footerSearchResultAccountInfo, BorderLayout.CENTER);
                footerPanel.add(header, BorderLayout.WEST);

                transLogBtn.setText(match.getId());
                transLogBtn.setFocusable(false);
                transLogBtn.setFont(new Font("Sans-serif", Font.BOLD, 25));
                transLogBtn.setBackground(Color.decode("#C0DEFF"));
                footerPanel.add(transLogBtn, BorderLayout.SOUTH);
                this.revalidate();
                this.repaint();
            } catch (NoSuchElementException e) {
                JOptionPane.showMessageDialog(null, "Finns ingen kund med det Customer ID / Account ID");
            }
        });

        transLogBtn.addActionListener(event -> {
            String customerId = transLogBtn.getText();
            System.out.println(customerId);
            Customer currentCustomer = Controller.getCustomerByIdOrAccountId(customerId);
            List<String> allTransactions = Controller.findMyTransactions(currentCustomer);
            showTransResult(currentCustomer, allTransactions);
        });
    }

    private void showTransResult(Customer customer, List<String> allTransactions) {
        Object[] options = {"Avsluta"};
        StringBuilder message = new StringBuilder();

        for(String line : allTransactions) {
            message.append("\n").append(line);
        }

        Object defaultValueWhenPressedEnter = options[0];
        JOptionPane.showOptionDialog(null, message.toString(), "Transaktioner för Kund: " + customer.getId(), JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, defaultValueWhenPressedEnter);
    }

    public int getAmountOfCustomersInBank() {
        return Controller.getAmountOfCustomers();
    }

    public List<String> getAllTrans() {
        return Controller.getAllTrans();
    }
}
