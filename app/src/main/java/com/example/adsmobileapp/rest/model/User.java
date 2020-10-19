package com.example.adsmobileapp.rest.model;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String name;
    private String last_name;
    private String email;
    private String password;
    private String token;


    public User(String id, String name, String last_name, String email, String password, String token) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public User(String name, String last_name, String email, String password) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }
}
