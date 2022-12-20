package org.example.Controller;

import org.example.Model.Customer;

import org.example.Model.ReadFile;
import org.example.Model.WriteFile;
import org.example.View.*;


import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.*;
import java.util.stream.Collectors;

public class Controller {

    private static ReadFile readFile = new ReadFile();
    private static WriteFile writeFile = new WriteFile();
    private static final Path customersFile = Path.of("src/main/resources/Customers.txt");
    private static final Path transactionsPath = Path.of("src/main/resources/Transactions.txt");
    public Controller(){
        new GuiFrame(new LoginPanel(), false);
    }

    public static Customer verifyLogin(String id, String password) throws NoSuchElementException {
        List<Customer> testList =  readFile.createListFromFile(customersFile);
        System.out.println(testList);
        return testList.stream()
                .filter(customer -> customer.getId().equalsIgnoreCase(id) && customer.getPassword().equals(password))
                .findFirst().orElseThrow(() -> new NoSuchElementException());
    }

    public static Customer getCustomerByIdOrAccountId(String id) throws NoSuchElementException {
        return readFile.createListFromFile(customersFile).stream()
                .filter(customer -> customer.getId().equalsIgnoreCase(id) || customer.getAccount().getAccountNumber().equalsIgnoreCase(id))
                .findFirst().orElseThrow(() -> new NoSuchElementException());
    }

    public static int getAmountOfCustomers() {
        return readFile.createListFromFile(customersFile).size();
    }



    public static List<String> getAllTrans() {
        return readFile.getTransactionsFromTxt(transactionsPath);
    }


    public static Customer getCustomerByAccountNr(String accountNumber) throws NoSuchElementException {
        return readFile.createListFromFile(customersFile).stream()
                .filter(customer -> customer.getAccount().getAccountNumber().equalsIgnoreCase(accountNumber))
                .findFirst().orElseThrow(() -> new NoSuchElementException());
    }

    public static void generateStringToTransactionLog(double amountToSend, Customer fromCustomer, Customer toCustomer){

        LocalDateTime ldt = LocalDateTime.now();
        String ldtFormatted = ldt.format(DateTimeFormatter.ofPattern("yy.MM.dd:HHmm")); // 221216:1530 (datum : klockslag)
        StringBuilder sb = new StringBuilder();

        sb
                .append(ldtFormatted)
                .append(", From ID: ")
                .append(fromCustomer.getId())
                .append(", ")
                .append(amountToSend)
                .append(" SEK")
                .append(", To: ")
                .append(toCustomer.getId())
                .append("\n");

        String textPackage = sb.toString();
        writeFile.saveTransactionToTransactionLog(textPackage);
    }

    public static void updateCustomerTransfer(double amount, Customer fromCustomer, Customer toCustomer) {
        //Vi har uppdaterat customer-objekten just nu. Bara spara till customer.txt

        // Predicate == Represents a predicate (boolean-valued function) of one argument.
        // typ: boolean isSame = fromCustomer.equals(toCustomer)
        Predicate<Customer> isFromCustomer = customer -> customer.getId().equals(fromCustomer.getId());
        Predicate<Customer> isToCustomer = customer -> customer.getId().equals(toCustomer.getId());
        List<Customer> customerList = readFile.createListFromFile(customersFile)
                //When we want to alter the inner state of an element, use peek instead of map (map is more convenient if we want to replace the element).
                .stream().peek(customer -> {
                    // Consumer == Represents an operation that accepts a single input argument and returns no result.
                    if (isFromCustomer.test(customer)) {
                        // Represents an operation that accepts a single input argument and returns no result
                        Consumer<Customer> reduceAmount = customerMatch -> customerMatch.getAccount().decreaseBalance(amount);
                        reduceAmount.accept(customer);
                    }

                    if (isToCustomer.test(customer)) {
                        Consumer<Customer> increaseAmount = customerMatch -> customerMatch.getAccount().increaseBalance(amount);
                        increaseAmount.accept(customer);
                    }
                }).toList();
        saveCustomerTransactionToCustomerTxtFormatter(customerList);
    }

    public static void saveCustomerTransactionToCustomerTxtFormatter(List<Customer> customerList) {

        StringBuilder stringToSend = new StringBuilder();

        for (Customer customer : customerList) {
            stringToSend.append(customer.getId())
                    .append("\n")
                    .append(customer.getName())
                    .append("\n")
                    .append(customer.getPassword())
                    .append("\n")
                    .append(customer.getDob().toString())
                    .append("\n")
                    .append(customer.getAccount().getAccountNumber())
                    .append("\n")
                    .append(customer.getAccount().getBalance())
                    .append("\n\n");
        }
        writeFile.updateCustomerTxt(String.valueOf(stringToSend));
    }

    public static boolean transferToOtherAccount(double amountToSend, Customer fromCustomer, Customer toCustomer) {

        if (validateFunds(fromCustomer, amountToSend)) {
            fromCustomer.getAccount().decreaseBalance(amountToSend);
            toCustomer.getAccount().increaseBalance(amountToSend);
            generateStringToTransactionLog(amountToSend, fromCustomer, toCustomer); //sparar en logg till transactions.txt
            updateCustomerTransfer(amountToSend, fromCustomer, toCustomer); //uppdaterar customer.txt
            return true;
        }
        return false;
    }

    public static boolean validateFunds(Customer customer, double amount) {
        return customer.getAccount().getBalance() - amount > 0;
    }

    public static List<String> findMyTransactions(Customer customer) {

        List<String> transactionsList = readFile.getTransactionsFromTxt(transactionsPath);

        // Denna fångar alla transactions mellan 1 kund BG/PG samt mellan 2 kunder om customer ID matchar
        return transactionsList
                .stream()
                .filter(line -> line.contains(customer.getId()))
                .collect(Collectors.toList());
    }

    public static boolean transferToBGPG(double amountToSend, Customer fromCustomer, String account, String type){
        if (validateFunds(fromCustomer, amountToSend)) {
            fromCustomer.getAccount().decreaseBalance(amountToSend);
            generateStringToTransactionLogBGPG(amountToSend, fromCustomer, account, type); //sparar en logg till transactions.txt
            updateCustomerTransferBGPG(amountToSend, fromCustomer); //uppdaterar customer.txt
            return true;
        }
        return false;
    }

    public static void updateCustomerTransferBGPG(double amount, Customer fromCustomer) {
        Predicate<Customer> isFromCustomer = customer -> customer.getId().equals(fromCustomer.getId());
        List<Customer> customerList = readFile.createListFromFile(customersFile)
                //When we want to alter the inner state of an element, use peek instead of map (map is more convenient if we want to replace the element).
                .stream().peek(customer -> {
                    // Consumer == Represents an operation that accepts a single input argument and returns no result.
                    if (isFromCustomer.test(customer)) {
                        // Represents an operation that accepts a single input argument and returns no result
                        Consumer<Customer> reduceAmount = customerMatch -> customerMatch.getAccount().decreaseBalance(amount);
                        reduceAmount.accept(customer);
                    }
                }).toList();
        saveCustomerTransactionToCustomerTxtFormatter(customerList);
    }

    public static void generateStringToTransactionLogBGPG(double amountToSend, Customer fromCustomer, String account, String type) {
        LocalDateTime ldt = LocalDateTime.now();
        String ldtFormatted = ldt.format(DateTimeFormatter.ofPattern("yy.MM.dd:HHmm")); // 221216:1530 (datum : klockslag)
        StringBuilder sb = new StringBuilder();

        sb
                .append(ldtFormatted)
                .append(", From ID: ")
                .append(fromCustomer.getId())
                .append(", ")
                .append(amountToSend)
                .append(" SEK")
                .append(", To: ")
                .append(type + " " + account)//ANTINGEN PG ELLER BG HÄR
                .append("\n");

        String textPackage = sb.toString();
        System.out.println(textPackage);
        writeFile.saveTransactionToTransactionLog(textPackage);
    }

    public static boolean isDouble(String number) {
        try{
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        new Controller();
    }
}
