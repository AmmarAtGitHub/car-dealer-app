package com.shamseddin.model;

import com.shamseddin.utils.PasswordHasher;

public class Admin {
    private int id;
    private final String username;
    private final String passwordHash;

    public Admin(int id, String username, String rawPassword) {
        this.id = id;
        this.username = username;
        this.passwordHash = PasswordHasher.hash(rawPassword);
    }
    
    // Constructor with pre-hashed password (for loading from database)
    public Admin(int id, String username, String passwordHash, boolean isHashed) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
 

    public boolean verifyPassword(String inputPassword) {
        return PasswordHasher.matches(inputPassword, passwordHash);
    }
}
