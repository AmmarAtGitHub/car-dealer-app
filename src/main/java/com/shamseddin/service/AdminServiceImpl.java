package com.shamseddin.service;

import com.shamseddin.dao.AdminDAO;
import com.shamseddin.model.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class AdminServiceImpl implements AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final AdminDAO dao;

    public AdminServiceImpl(AdminDAO dao) {
        this.dao = dao;
    }

    @Override
    public Admin registerAdmin(String username, String rawPassword) {
        if (username == null || username.isBlank() || rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("username and password required");
        }
        // check duplicate username
        Optional<Admin> existing = dao.getAdminByUsername(username);
        if (existing.isPresent()) throw new IllegalArgumentException("username already exists");

        Admin toCreate = new Admin(0, username, rawPassword);
        Admin created = dao.addAdmin(toCreate);
        logger.info("Registered new admin: {}", username);
        return created;
    }

    @Override
    public boolean login(String username, String rawPassword) {
        return dao.authenticate(username, rawPassword);
    }

    @Override
    public Optional<Admin> getAdminById(int id) {
        return dao.getAdminById(id);
    }

    @Override
    public void updateAdmin(int id, String newUsername, String newPassword) {
        dao.updateAdmin(id, newUsername, newPassword);
    }

    @Override
    public void deleteAdmin(int id) {
        dao.deleteAdminById(id);
    }
}
