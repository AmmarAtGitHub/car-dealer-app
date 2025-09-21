package com.shamseddin.service;

import com.shamseddin.model.Transaction;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Transaction addTransaction(Transaction transaction);
    Optional<Transaction> getTransactionById(int id);
    List<Transaction> getTransactionsByCustomerId(int customerId);
    List<Transaction> getTransactionsByVehicleId(int vehicleId);
    List<Transaction> getAllTransactions();
}
