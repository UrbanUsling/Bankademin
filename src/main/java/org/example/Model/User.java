package org.example.Model;

import java.time.LocalDate;

public abstract class User {

    private String id;
    private String name;
    private String password;
    private LocalDate dob;

    public User(){

    }

    public User(String id, String name, String password, LocalDate dob){
        this.id = id;
        this.name = name;
        this.password = password;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDob() { return dob;}

    @Override
    public String toString() {
        return "\n----" + "\n" + "ID: " + id + "\nName: " + name + "\npassword: " + password + "\ndob: " + dob;
    }
}