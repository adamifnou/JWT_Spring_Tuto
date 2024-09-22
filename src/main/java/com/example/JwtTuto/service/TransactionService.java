package com.example.JwtTuto.service;

import com.example.JwtTuto.constant.TransactionType;
import com.example.JwtTuto.entity.MaterialTransaction;


public interface TransactionService {
    void saveTransaction(MaterialTransaction transaction);
    MaterialTransaction getTransactionById(int id);
    Iterable<MaterialTransaction> getAllTransactions();
    Iterable<MaterialTransaction> getTransactionsByMaterialId(int materialId);
    Iterable<MaterialTransaction> getTransactionsByExecutorId(int executorId);
    Iterable<MaterialTransaction> getTransactionsByTransactionType(TransactionType transactionType);
    Iterable<MaterialTransaction> getTransactionsByDate(String date);
    Iterable<MaterialTransaction> getTransactionsByDateRange(String startDate, String endDate);
    Iterable<MaterialTransaction> getTransactionsByMaterialIdAndDateRange(int materialId, String startDate, String endDate);
}
