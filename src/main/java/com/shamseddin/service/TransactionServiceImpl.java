package com.shamseddin.service;

import com.shamseddin.dao.TransactionDAO;
import com.shamseddin.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final TransactionDAO dao;

    public TransactionServiceImpl(TransactionDAO dao) {
        this.dao = dao;
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        // Basic validation
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        // Additional validation could be added here (e.g., check if customer/vehicle exists)

        Transaction created = dao.addTransaction(transaction);
        logger.info("Created new transaction with ID: {}", created.getId());
        return created;
    }

    @Override
    public Optional<Transaction> getTransactionById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid transaction ID");
        }
        return dao.getTransactionById(id);
    }

    @Override
    public List<Transaction> getTransactionsByCustomerId(int customerId) {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Invalid customer ID");
        }
        return dao.getTransactionsByCustomerId(customerId);
    }

    @Override
    public List<Transaction> getTransactionsByVehicleId(int vehicleId) {
        if (vehicleId <= 0) {
            throw new IllegalArgumentException("Invalid vehicle ID");
        }
        return dao.getTransactionsByVehicleId(vehicleId);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return dao.getAllTransactions();
    }
}
