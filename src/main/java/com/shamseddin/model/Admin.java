package com.shamseddin.model;

import com.shamseddin.utils.PasswordHasher;

public class Admin {
    private final int id;
    private final String username;
    private final String passwordHash;

    public Admin(int id, String username, String rawPassword) {
        this.id = id;
        this.username = username;
        this.passwordHash = PasswordHasher.hash(rawPassword);
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }
 

    public boolean verifyPassword(String inputPassword) {
        return PasswordHasher.matches(inputPassword, passwordHash);
    }
}
