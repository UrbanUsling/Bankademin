package org.example.View;

import org.example.Model.Admin;
import org.example.Model.Customer;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {

    private JButton buttonOne = new JButton("Hem");
    private JButton buttonTwo = new JButton("Mina konton");
    private JButton buttonThree = new JButton("Betala och överföra");
    private JButton buttonFour = new JButton("Om oss & kontakt");

    private JButton adminBtn = new JButton("Admin Panel");

    private boolean isLoggedIn;
    private Admin currentAdmin;
    private Customer currentCustomer;

    private boolean adminMode;


    public HeaderPanel(boolean isLoggedIn, Customer currentCustomer, Admin currentAdmin){
        this.currentCustomer = currentCustomer;
        this.isLoggedIn = isLoggedIn;
        this.currentAdmin = currentAdmin;
        this.setLayout(new GridBagLayout());

        loadMenu(isLoggedIn);

        this.setBackground(Color.decode("#4B56D2"));
        this.setPreferredSize(new Dimension(600, 100));
        addListeners();
    }

    public void loadMenu(boolean isLoggedIn) {
        if(isLoggedIn && currentAdmin != null) {// Dvs om man är admin
            JButton[] adminButtons = {buttonOne, adminBtn};
            loadButtons(adminButtons);
            adminMode = true;
        } else if (isLoggedIn) {
            JButton[] buttons = {buttonOne, buttonTwo, buttonThree, buttonFour};
            loadButtons(buttons);
        } else {
            JLabel header = new JLabel("Welcome to Bankademin");
            header.setFont(new Font("Sans-serif", Font.BOLD, 30));
            this.add(header);
        }
    }

    public void loadButtons(JButton[] buttons) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(new Font("Sans-serif", Font.BOLD, 25));
            buttons[i].setFocusable(false);
            buttons[i].setBackground(Color.decode("#C0DEFF"));
            this.add(buttons[i]);
            this.add(Box.createHorizontalStrut(15));
        }
    }

    public void addListeners() {
        buttonOne.addActionListener(event -> {
            if(currentCustomer != null && !adminMode) {
                Container parent = getParent();
                parent.removeAll();
                parent.add(new HeaderPanel(true, currentCustomer, null), BorderLayout.NORTH);
                parent.add(new HomePanel(currentCustomer, false), BorderLayout.CENTER);
                parent.revalidate();
                parent.repaint();
            } else if (currentAdmin != null && adminMode) {
                Container parent = getParent();
                parent.removeAll();
                parent.add(new HeaderPanel(true, null, currentAdmin), BorderLayout.NORTH);
                parent.add(new HomePanel(null, true), BorderLayout.CENTER);
                parent.revalidate();
                parent.repaint();
            }
        });

        buttonTwo.addActionListener(event -> {
            if(currentCustomer != null && !adminMode) {
                Container parent = getParent();
                parent.removeAll();
                parent.add(new HeaderPanel(true, currentCustomer, null), BorderLayout.NORTH);
                parent.add(new MyAccountsPanel(currentCustomer), BorderLayout.CENTER);
                parent.revalidate();
                parent.repaint();
            }
        });

        buttonThree.addActionListener(event -> {
            if(currentCustomer != null && !adminMode) {
                Container parent = getParent();
                parent.removeAll();
                parent.add(new HeaderPanel(true, currentCustomer, null), BorderLayout.NORTH);
                parent.add(new TransferPanel(currentCustomer), BorderLayout.CENTER);
                parent.revalidate();
                parent.repaint();
            }
        });

        buttonFour.addActionListener(event -> {
            if(currentCustomer != null && !adminMode) {
                Container parent = getParent();
                parent.removeAll();
                parent.add(new HeaderPanel(true, currentCustomer, null), BorderLayout.NORTH);
                parent.add(new ContactPanel(currentCustomer), BorderLayout.CENTER);
                parent.revalidate();
                parent.repaint();
            }
        });

        adminBtn.addActionListener(event -> {
            if(currentCustomer == null && adminMode) {
                Container parent = getParent();
                parent.removeAll();
                parent.add(new HeaderPanel(true, null, currentAdmin), BorderLayout.NORTH);
                parent.add(new AdminPanel(), BorderLayout.CENTER);
                parent.revalidate();
                parent.repaint();
            }
        });
    }
}

