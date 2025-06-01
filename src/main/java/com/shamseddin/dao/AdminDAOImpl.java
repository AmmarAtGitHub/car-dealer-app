package com.shamseddin.dao;

import com.shamseddin.db.DatabaseConnection;
import com.shamseddin.model.Admin;
import com.shamseddin.utils.PasswordHasher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Optional;

/**
 * JDBC implementation of AdminDAO interface.
 * Handles CRUD operations on Admin data using MySQL.
 */
public class AdminDAOImpl implements AdminDAO {

    private static final Logger logger = LoggerFactory.getLogger(AdminDAOImpl.class);

    private final Connection conn;

    public AdminDAOImpl() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            logger.error("Failed to connect to the database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Admin addAdmin(Admin admin) {
        String sql = "INSERT INTO admins (username, password_hash) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPasswordHash());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Successfully inserted Admin with username: {}", admin.getUsername());

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        return new Admin(generatedId, admin.getUsername(), admin.getPasswordHash());
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error inserting admin", e);
            throw new RuntimeException(e);
        }

        return null; 
    }

    @Override
    public void deleteAdminById(int id) {
        String sql = "DELETE FROM admins WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Successfully deleted admin with ID: {}", id);
            } else {
                logger.warn("No admin found with ID: {} to delete", id);
            }
        } catch (SQLException e) {
            logger.error("Error deleting admin with ID: {}", id, e);
            throw new RuntimeException(e); // Propagate exception after logging
        }
    }
    @Override
    public Optional<Admin> getAdminById(int id) {
        String sql = "SELECT * FROM admins WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("username");
                    String passwordHash = rs.getString("password_hash");

                    Admin admin = new Admin(id, username, passwordHash, true); // use trusted constructor
                    logger.info("Admin found with ID: {}", id);
                    return Optional.of(admin);
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching admin by ID", e);
            throw new RuntimeException("Database error while fetching admin", e);
        }

        logger.warn("No admin found with ID: {}", id);
        return Optional.empty();
    }

    @Override
    public boolean authenticate(String username, String password) {
        String sql = "SELECT password_hash FROM admins WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    boolean matched = PasswordHasher.matches(password, storedHash);

                    if (matched) {
                        logger.info("Authentication successful for username: {}", username);
                    } else {
                        logger.warn("Authentication failed for username: {}", username);
                    }
                    return matched;
                } else {
                    logger.warn("Authentication failed: username '{}' not found", username);
                    return false;
                }
            }
        } catch (SQLException e) {
            logger.error("Error during authentication for username: {}", username, e);
            throw new RuntimeException("Database error during authentication", e);
        }
    }


    @Override
    public void updateAdmin(int id, String username, String password) {
        String sql = "UPDATE admins SET username = ?, password_hash = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            // Hash the new password before storing
            String hashedPassword = PasswordHasher.hash(password);
            stmt.setString(2, hashedPassword);

            stmt.setInt(3, id);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                logger.info("Updated admin with ID: {}", id);
            } else {
                logger.warn("No admin found with ID: {}. Update skipped.", id);
            }
        } catch (SQLException e) {
            logger.error("Failed to update admin with ID: {}", id, e);
            throw new RuntimeException("Database error during admin update", e);
        }
    }




}
