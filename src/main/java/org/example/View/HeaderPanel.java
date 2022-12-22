package org.example.View;

import org.example.Model.Customer;
import org.example.Model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HeaderPanel extends JPanel {

    private JButton buttonOne = new JButton("Hem");
    private JButton buttonTwo = new JButton("Mina konton");
    private JButton buttonThree = new JButton("Betala och överföra");
    private JButton buttonFour = new JButton("Om oss & kontakt");
    private JButton buttonFive = new JButton("Ändra Om oss & kontakt");
    private JButton buttonSix = new JButton("Ändra Hemmeddelande");
    private ArrayList<JButton> adminButtons = new ArrayList<>();//lista med adminknappar


    private boolean isLoggedIn;
    private boolean isAdmin = false;

    private User currentCustomer;


    public HeaderPanel(boolean isAdmin, boolean isLoggedIn, User currentCustomer){
        //Lagt till en till boolean, isAdmin, samt ändrat Customer till User. Både admin och customer är user
        this.isAdmin = isAdmin;
        this.currentCustomer = currentCustomer;
        this.isLoggedIn = isLoggedIn;
        this.setLayout(new GridBagLayout());

        loadMenu(isLoggedIn);

        this.setBackground(Color.decode("#4B56D2"));
        this.setPreferredSize(new Dimension(600, 100));
        addListeners();
    }

    public void loadMenu(boolean isLoggedIn) {
        if(isLoggedIn && !isAdmin) {//lagt till ett till villkor. Admin får ej dessa knappar
            JButton[] buttons = {buttonOne, buttonTwo, buttonThree, buttonFour};
            loadButtons(buttons);
        } else if (isAdmin){
            loadAdminButtons();
            this.add(Box.createHorizontalStrut(15));
        }else{
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
    public void loadAdminButtons() {//adminknapparna
        adminButtons.add(buttonOne);
        adminButtons.add(buttonFour);
        adminButtons.add(buttonFive);
        adminButtons.add(buttonSix);
        for (JButton button : adminButtons) {

            button.setFont(new Font("Sans-serif", Font.BOLD, 12));
            button.setFocusable(false);
            button.setBackground(Color.decode("#C0DEFF"));
            this.add(button);
        }
            this.add(Box.createHorizontalStrut(15));
        }

    public void addListeners() {
        buttonOne.addActionListener(event -> {
            if(currentCustomer != null||isAdmin) {//lägger till knappen för admin med
                Container parent = getParent();
                parent.removeAll();
                parent.add(new HeaderPanel(isAdmin, true, currentCustomer), BorderLayout.NORTH);
                parent.add(new HomePanel(currentCustomer), BorderLayout.CENTER);
                parent.revalidate();
                parent.repaint();
            }
        });

        buttonTwo.addActionListener(event -> {
            if(currentCustomer != null) {
                Container parent = getParent();
                parent.removeAll();
                parent.add(new HeaderPanel(isAdmin, true, currentCustomer), BorderLayout.NORTH);
                parent.add(new MyAccountsPanel(currentCustomer), BorderLayout.CENTER);
                parent.revalidate();
                parent.repaint();
            }
        });

        buttonThree.addActionListener(event -> {
            if(currentCustomer != null) {
                Container parent = getParent();
                parent.removeAll();
                parent.add(new HeaderPanel(isAdmin, true, currentCustomer), BorderLayout.NORTH);
                parent.add(new TransferPanel(currentCustomer), BorderLayout.CENTER);
                parent.revalidate();
                parent.repaint();
            }
        });

        buttonFour.addActionListener(event -> {
            Container parent = getParent();
            parent.removeAll();
            parent.add(new HeaderPanel(isAdmin , true, currentCustomer), BorderLayout.NORTH);
            parent.add(new ContactPanel(currentCustomer), BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        });
        buttonFive.addActionListener(event -> {
            //Knapp fem för att öppna konstruktor av contactpanel som tillåter ändring i kontaktinfo
            Container parent = getParent();
            parent.removeAll();
            parent.add(new HeaderPanel(isAdmin , true, currentCustomer), BorderLayout.NORTH);
            parent.add(new ContactPanel(), BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        });
        buttonSix.addActionListener(event -> {
            //Knapp fem för att öppna konstruktor av homepanel som tillåter ändring i kontaktinfo
            Container parent = getParent();
            parent.removeAll();
            parent.add(new HeaderPanel(isAdmin , true, currentCustomer), BorderLayout.NORTH);
            parent.add(new HomePanel(), BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        });
    }
}

