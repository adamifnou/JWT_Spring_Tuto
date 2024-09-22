package com.example.JwtTuto.repository;

import com.example.JwtTuto.constant.TransactionType;
import com.example.JwtTuto.entity.MaterialTransaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;


public interface TransactionRepository extends JpaRepository<MaterialTransaction, Integer> {
    Iterable<MaterialTransaction> findByMaterialId(int materialId);
    Iterable<MaterialTransaction> findMaterialTransactionsByExecutor_Id(int executorId);
    Iterable<MaterialTransaction> findMaterialTransactionsByTransactionType(TransactionType transactionType);
    Iterable<MaterialTransaction> findMaterialTransactionsByTransactionDate(LocalDateTime transactionDate);
    Iterable<MaterialTransaction> findMaterialTransactionsByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    Iterable<MaterialTransaction> findMaterialTransactionsByMaterialIdAndTransactionDateBetween(int materialId, LocalDateTime startDate, LocalDateTime endDate);
}
