package org.example.Model;

import java.time.LocalDate;

public class Customer extends User {

    private Account account;

    public Customer(String id, String name, String password, LocalDate dob, Account account){
        super(id, name, password, dob);
        this.account = account;
    }

    @Override
    public String toString() {
        return super.toString() + account;
    }

    public Account getAccount() {
        return account;
    }
}