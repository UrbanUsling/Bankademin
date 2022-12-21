package org.example.Model;

import java.time.LocalDate;

public class Admin extends User {
    public Admin(String id, String name, String password, LocalDate dob) {
        super(id, name, password, dob);
    }

}