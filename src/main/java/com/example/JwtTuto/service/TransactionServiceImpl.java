package com.example.JwtTuto.service;

import com.example.JwtTuto.constant.TransactionType;
import com.example.JwtTuto.entity.MaterialTransaction;
import com.example.JwtTuto.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(MaterialTransaction transaction) {
        try {
            if(transaction.getMaterial() == null){
                throw new RuntimeException("Material cannot be null");
            }
            if(transaction.getTransactionDate() == null){
                throw new RuntimeException("Transaction date cannot be null");
            }
            if(transaction.getTransactionType() == null){
                throw new RuntimeException("Transaction type cannot be null");
            }
            if(transaction.getQuantity() <= 0){
                throw new RuntimeException("Quantity must be greater than 0");
            }
            transactionRepository.save(transaction);
        } catch (Exception e){
            throw new RuntimeException("Error saving transaction: " + e.getMessage());
        }
    }

    @Override
    public MaterialTransaction getTransactionById(int id) {
        try {
            return transactionRepository.findById(id).orElse(null);
        } catch (Exception e){
            throw new RuntimeException("Error getting transaction by id: " + e.getMessage());
        }
    }

    @Override
    public Iterable<MaterialTransaction> getAllTransactions() {
        try {
            return transactionRepository.findAll();
        } catch (Exception e){
            throw new RuntimeException("Error getting all transactions: " + e.getMessage());
        }
    }

    @Override
    public Iterable<MaterialTransaction> getTransactionsByMaterialId(int materialId) {
        try {
            return transactionRepository.findByMaterialId(materialId);
        } catch (Exception e){
            throw new RuntimeException("Error getting transactions by material id: " + e.getMessage());
        }
    }

    @Override
    public Iterable<MaterialTransaction> getTransactionsByExecutorId(int executorId) {
        try {
            return transactionRepository.findMaterialTransactionsByExecutor_Id(executorId);
        } catch (Exception e){
            throw new RuntimeException("Error getting transactions by executor id: " + e.getMessage());
        }
    }

    @Override
    public Iterable<MaterialTransaction> getTransactionsByTransactionType(TransactionType transactionType) {
        try {
            return transactionRepository.findMaterialTransactionsByTransactionType(transactionType);
        } catch (Exception e){
            throw new RuntimeException("Error getting transactions by transaction type: " + e.getMessage());
        }
    }

    @Override
    public Iterable<MaterialTransaction> getTransactionsByDate(String date) {
        try {
            LocalDateTime localDate = LocalDateTime.parse(date);
            return transactionRepository.findMaterialTransactionsByTransactionDate(localDate);
        } catch (Exception e){
            throw new RuntimeException("Error getting transactions by date: " + e.getMessage());
        }
    }

    @Override
    public Iterable<MaterialTransaction> getTransactionsByDateRange(String startDate, String endDate) {
        try {
            LocalDateTime start = LocalDateTime.parse(startDate);
            LocalDateTime end = LocalDateTime.parse(endDate);
            return transactionRepository.findMaterialTransactionsByTransactionDateBetween(start, end);
        } catch (Exception e){
            throw new RuntimeException("Error getting transactions by date range: " + e.getMessage());
        }
    }

    @Override
    public Iterable<MaterialTransaction> getTransactionsByMaterialIdAndDateRange(int materialId, String startDate, String endDate) {
        try {
            LocalDateTime start = LocalDateTime.parse(startDate);
            LocalDateTime end = LocalDateTime.parse(endDate);
            return transactionRepository.findMaterialTransactionsByMaterialIdAndTransactionDateBetween(materialId, start, end);
        } catch (Exception e){
            throw new RuntimeException("Error getting transactions by material id and date range: " + e.getMessage());
        }
    }
}