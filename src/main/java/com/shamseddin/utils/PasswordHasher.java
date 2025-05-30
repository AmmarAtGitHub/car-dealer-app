package com.shamseddin.utils;

import org.mindrot.jbcrypt.BCrypt;



public class PasswordHasher {
    public static String hash(String plainPassword){
        // Hash the password (use when creating new Admin accounts)
        return BCrypt.hashpw(plainPassword ,BCrypt.gensalt());
    
}
    // Check password (at login)
public static boolean matches(String plainPassword, String hashedPassword){
    return BCrypt.checkpw(plainPassword, hashedPassword);
}

public static void main(String[] args) {
    var hashedPassword = hash("password");
    System.out.println(hashedPassword);
    System.out.println(matches("password", hashedPassword));
}


}

