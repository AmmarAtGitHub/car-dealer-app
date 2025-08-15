package com.shamseddin.dao;

import com.shamseddin.model.Admin;

import java.util.Optional;

public interface AdminDAO {
     Admin addAdmin(Admin admin); // // returns the created Admin with ID
     void updateAdmin(int id, String username, String password);
     void deleteAdminById(int id);
     Optional<Admin> getAdminById(int id);
     Optional<Admin> getAdminByUsername(String username);
     boolean authenticate(String username, String password);
    

}

