package org.example.View;
import javax.swing.*;
import java.awt.*;

public class GuiFrame extends JFrame {

    private JPanel currentBody;

    private HeaderPanel headerPanel;

    private boolean isLoggedIn;

    public GuiFrame(JPanel currentBody, boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
        // Skulle kunna göra såhär för att enkelt kunna uppdatera currentBody, vilket är det enda som ändras i GUI när
        // man öppnar en ny vy.
        this.currentBody = currentBody;

        headerPanel = new HeaderPanel(isLoggedIn, null);

        this.add(headerPanel, BorderLayout.NORTH);
        this.add(currentBody, BorderLayout.CENTER);

        //Eftersom att vi har alla size-grejer här så bör vi inte behöva ställa in ett skit i respektive "body" class. Däremot så behöver vi bestämma Headerns dimensioner i Header, misstänker jag.
        this.setBackground(Color.BLUE);
        this.add(currentBody);
        this.setVisible(true);
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Bankademin App");
        this.setResizable(false);
    }

}