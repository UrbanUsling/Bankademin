package org.example.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {

    public List<Customer> createListFromFile(Path filePath) {
        int counter = 0;
        String line;
        List<Customer> customers = new ArrayList<>();
        List<String> currentCustomer = new ArrayList<>();

        try (BufferedReader in = Files.newBufferedReader(filePath)) {
            while ((line = in.readLine()) != null) {
                currentCustomer.add(line);
                counter++;
                if(counter == 7){

                    Customer current = new Customer(currentCustomer.get(0),currentCustomer.get(1),currentCustomer.get(2),LocalDate.parse(currentCustomer.get(3)),
                            new Account(currentCustomer.get(4), Double.parseDouble(currentCustomer.get(5))));

                    counter = 0;
                    customers.add(current);
                    currentCustomer.clear();
                }

            }
        } catch (IOException e) {
            System.out.println("Inläsning av fil misslyckades. Säkerställ att den ligger i src-mappen.");
        }
        return customers;
    }

    public List<String> getTransactionsFromTxt(Path filePath) {

        List<String> listOfTransactions = new ArrayList<>();
        String line;
        try (BufferedReader in = Files.newBufferedReader(filePath)) {
            while ((line = in.readLine()) != null) {
                listOfTransactions.add(line);
            }
        } catch (IOException e) {
            System.out.println("Inläsning av fil misslyckades. Säkerställ att den ligger i src-mappen.");
        }
        return listOfTransactions;
    }

}