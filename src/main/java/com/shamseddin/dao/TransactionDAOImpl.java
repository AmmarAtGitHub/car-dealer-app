package com.shamseddin.dao;

import com.shamseddin.db.DatabaseConnection;
import com.shamseddin.model.Transaction;
import com.shamseddin.model.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import com.shamseddin.model.VehicleStatus;

public class TransactionDAOImpl implements TransactionDAO {
    private static final Logger logger = LoggerFactory.getLogger(TransactionDAOImpl.class);
    private final Connection conn;

    public TransactionDAOImpl() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            logger.error("Failed to connect to DB", e);
            throw new RuntimeException("Could not connect to DB", e);
        }
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (vehicle_id, buyer_id, seller_id, admin_id, " +
                   "sale_price, purchase_price, transaction_date, payment_method, type, status) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, transaction.getVehicleId());
            
            // Handle nullable buyer_id and seller_id
            if (transaction.getBuyerId() != null) {
                stmt.setInt(2, transaction.getBuyerId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            
            if (transaction.getSellerId() != null) {
                stmt.setInt(3, transaction.getSellerId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            
            stmt.setInt(4, transaction.getAdminId());
            
            // Set appropriate price based on transaction type
            if (transaction.getType() == TransactionType.SALE) {
                stmt.setBigDecimal(5, transaction.getSalePrice());
                stmt.setNull(6, Types.DECIMAL);
            } else {
                stmt.setNull(5, Types.DECIMAL);
                stmt.setBigDecimal(6, transaction.getPurchasePrice());
            }
            
            stmt.setTimestamp(7, Timestamp.valueOf(transaction.getTransactionDate()));
            stmt.setString(8, transaction.getPaymentMethod());
            stmt.setString(9, transaction.getType().name());
            stmt.setString(10, transaction.getStatus().name());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Adding transaction failed, no rows affected");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    transaction.setId(rs.getInt(1));
                }
            }

            logger.info("Added transaction ID: {}", transaction.getId());
            return transaction;

        } catch (SQLException e) {
            logger.error("Error adding transaction", e);
            throw new RuntimeException("Database error while adding transaction", e);
        }
    }

    @Override
    public Optional<Transaction> getTransactionById(int id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToTransaction(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching transaction by ID: {}", id, e);
            throw new RuntimeException("Database error while fetching transaction", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Transaction> getTransactionsByCustomerId(int customerId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE buyer_id = ? OR seller_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRowToTransaction(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching transactions for customer ID: {}", customerId, e);
            throw new RuntimeException("Database error while fetching transactions", e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByVehicleId(int vehicleId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE vehicle_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vehicleId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRowToTransaction(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching transactions for vehicle ID: {}", vehicleId, e);
            throw new RuntimeException("Database error while fetching transactions", e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY transaction_date DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                transactions.add(mapRowToTransaction(rs));
            }
        } catch (SQLException e) {
            logger.error("Error fetching all transactions", e);
            throw new RuntimeException("Database error while fetching transactions", e);
        }
        return transactions;
    }

    private Transaction mapRowToTransaction(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int vehicleId = rs.getInt("vehicle_id");
        Integer buyerId = rs.getObject("buyer_id", Integer.class);
        Integer sellerId = rs.getObject("seller_id", Integer.class);
        int adminId = rs.getInt("admin_id");
        TransactionType type = TransactionType.valueOf(rs.getString("type"));
        BigDecimal price = type == TransactionType.SALE ? 
                         rs.getObject("sale_price", BigDecimal.class) :
                         rs.getObject("purchase_price", BigDecimal.class);
        String paymentMethod = rs.getString("payment_method");
        
        // Create transaction with appropriate constructor based on type
        Transaction transaction = new Transaction(
            vehicleId,
            buyerId,
            sellerId,
            adminId,
            type,
            price,
            paymentMethod
        );
        
        transaction.setId(id);
        transaction.setStatus(VehicleStatus.valueOf(rs.getString("status")));
        return transaction;
    }
}
