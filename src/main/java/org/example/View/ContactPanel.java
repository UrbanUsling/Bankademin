package org.example.View;

import org.example.Model.Customer;
import org.example.Model.User;

import javax.swing.*;
import java.awt.*;

public class ContactPanel extends JPanel {

    private JTextArea textArea = new JTextArea(20, 20);
    private static JTextArea updatedText = new JTextArea(20, 50);
    private static boolean changedText= false;
    private JLabel message = new JLabel("Skriv nytt meddelande under");
    private User currentCustomer;
    private String contactInfoHeader;
    private String contactInfoBody;
    private String aboutHeader;
    private String aboutBody;

    private JLabel ledning = new JLabel(new ImageIcon("src/main/resources/ledning.jpg"));

    public ContactPanel(User currentCustomer){
        this.currentCustomer = currentCustomer;
        if (!changedText) {
            contactInfoHeader = "\nKontaktinformation:\n";
            contactInfoBody =
                    "Telefon: 072-209 14 10\n" +
                            "Email: Bankademin.support@livesupport.india\n" +
                            "Adress: Tomtebodavägen 3A, 171 65 Solna\n\n";

            aboutHeader = "Vi är Bankademins ledningsgrupp:\n";
            aboutBody =
                    "Hatte Von Strohlwasser\n" +
                            "Hatte har varit verksam i bankväsendet sedan 1978.\n" +
                            "Hatte blev medlem i ledningsgruppen och delägare till Bankdemin 1995 efter att han personligen\n" +
                            "räddade landet från Bank-, finans- och fastighetskrisen 1990.\n\n" +
                            "Ikaros Tass\n" +
                            "Född 2004. Ikaros är med råge inte bara vår yngsta medlem i ledningsgruppen,\n" +
                            "utan också den yngsta personen på hela företaget.\n\n" +
                            "Karl Hamilton\n" +
                            "Hemligt.\n\n" +
                            "Otto SilverHielm\n" +
                            "Otto var ordförande för studentkåren på den prestigefyllda internatskolan Solbacka.\n\n";

            textArea.append(contactInfoHeader);
            textArea.append(contactInfoBody);
            textArea.append(aboutHeader);
            textArea.append(aboutBody);
        }else {
            textArea = updatedText;
        }
        textArea.setFont(new Font("Sans-serif", Font.BOLD, 18));
        textArea.setEditable(false);
        this.add(textArea);
        this.add(ledning);

        this.setBackground(Color.WHITE);
        revalidate();
        repaint();
    }
    public ContactPanel(){//för admin att ändra kontaktinfo
        updatedText.setFont(new Font("Sans-serif", Font.BOLD, 18));
        updatedText.setEditable(true);
        this.add(message);
        this.add(updatedText, BorderLayout.CENTER);
        this.setBackground(Color.WHITE);
        setTextArea(updatedText);
        changedText = !updatedText.getText().equals("");
        this.revalidate();
        this.repaint();
    }
    private void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

}
