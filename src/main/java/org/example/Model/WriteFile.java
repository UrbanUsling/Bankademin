package org.example.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class WriteFile {
    public void saveTransactionToTransactionLog(String transaction) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(Path.of("src/main/resources/Transactions.txt").toFile(), true))) {
            out.write(transaction);
        } catch (IOException e) {
            System.out.println("Inläsning av fil misslyckades. Säkerställ att Path är korrekt.");
        }
    }

    public void updateCustomerTxt(String lines) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(Path.of("src/main/resources/Customers.txt").toFile()))) {
            out.write(lines);
            out.newLine();
        } catch (IOException e) {
            System.out.println("Inläsning av fil misslyckades. Säkerställ att Path är korrekt.");
        }

    }
}