package org.example.View;



import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {
    public AdminPanel() {
        this.setLayout(new FlowLayout());
        JLabel test = new JLabel("Admin panel");
        JLabel test2 = new JLabel("Admin panel");
        JLabel test3 = new JLabel("Admin panel");
        this.add(test);
        this.add(test2);
        this.add(test3);
    }
}
