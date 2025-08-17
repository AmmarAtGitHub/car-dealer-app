package com.shamseddin.dao;

import com.shamseddin.db.DatabaseConnection;
import com.shamseddin.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO {
    private static final Logger logger = LoggerFactory.getLogger(CustomerDAOImpl.class);
    private final Connection conn;

    public CustomerDAOImpl() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            logger.error("Failed to open DB connection", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (first_name, last_name, email, phone, bank_account, street, zip_code, city, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhone());
            // bank account optional
            String bank = customer.getBankAccount();
            if (bank == null || bank.trim().isEmpty()) {
                stmt.setNull(5, Types.VARCHAR);
            } else {
                stmt.setString(5, bank);
            }
            stmt.setString(6, customer.getStreet());
            stmt.setString(7, customer.getZipCode());
            stmt.setString(8, customer.getCity());
            stmt.setString(9, customer.getCountry());
            int rows = stmt.executeUpdate();
            if (rows == 0) throw new SQLException("Insert failed");
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    customer.setId(rs.getInt(1));
                    return customer;
                }
            }
        } catch (SQLException e) {
            logger.error("Error adding customer", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET first_name=?, last_name=?, email=?, phone=?, bank_account=?, street=?, zip_code=?, city=?, country=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhone());
            String bank = customer.getBankAccount();
            if (bank == null || bank.trim().isEmpty()) stmt.setNull(5, Types.VARCHAR);
            else stmt.setString(5, bank);
            stmt.setString(6, customer.getStreet());
            stmt.setString(7, customer.getZipCode());
            stmt.setString(8, customer.getCity());
            stmt.setString(9, customer.getCountry());
            stmt.setInt(10, customer.getId());
            int rows = stmt.executeUpdate();
            if (rows == 0) throw new SQLException("No customer found");
        } catch (SQLException e) {
            logger.error("Error updating customer", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCustomerById(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting customer id {}", id, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Customer> getCustomerById(int id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching customer id {}", id, e);
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            logger.error("Error fetching customer by email {}", email, e);
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e) {
            logger.error("Error fetching all customers", e);
            throw new RuntimeException(e);
        }
    }

    private Customer mapRow(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setId(rs.getInt("id"));
        c.setFirstName(rs.getString("first_name"));
        c.setLastName(rs.getString("last_name"));
        c.setEmail(rs.getString("email"));
        c.setPhone(rs.getString("phone"));
        c.setBankAccount(rs.getString("bank_account"));
        c.setStreet(rs.getString("street"));
        c.setZipCode(rs.getString("zip_code"));
        c.setCity(rs.getString("city"));
        c.setCountry(rs.getString("country"));
        return c;
    }
}
