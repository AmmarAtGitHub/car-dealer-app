package com.shamseddin.dao;

import com.shamseddin.model.Transaction;
import java.util.List;
import java.util.Optional;

public interface TransactionDAO {
    Transaction addTransaction(Transaction transaction);
    Optional<Transaction> getTransactionById(int id);
    List<Transaction> getTransactionsByCustomerId(int customerId);
    List<Transaction> getTransactionsByVehicleId(int vehicleId);
    List<Transaction> getAllTransactions();
    // Optional: void updateTransaction(Transaction transaction);
    // Optional: void deleteTransaction(int id);
}
