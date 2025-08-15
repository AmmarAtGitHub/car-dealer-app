package com.shamseddin.service;

import com.shamseddin.model.Admin;

import java.util.Optional;

public interface AdminService {
    Admin registerAdmin(String username, String rawPassword);
    boolean login(String username, String rawPassword);
    Optional<Admin> getAdminById(int id);
    void updateAdmin(int id, String newUsername, String newPassword);
    void deleteAdmin(int id);
}
