package com.example.JwtTuto.controller;

import com.example.JwtTuto.constant.TransactionType;
import com.example.JwtTuto.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@AllArgsConstructor
@RestController
@RequestMapping("/adminAPI/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllTransactions() {
        try {
            return ResponseEntity.ok(transactionService.getAllTransactions());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getTransaction")
    public ResponseEntity<Object> getTransactionById(@RequestParam int id) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getTransactionsByMaterialId")
    public ResponseEntity<Object> getTransactionsByMaterialId(@RequestParam int materialId) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionsByMaterialId(materialId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getTransactionsByExecutorId")
    public ResponseEntity<Object> getTransactionsByExecutorId(@RequestParam int executorId) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionsByExecutorId(executorId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getTransactionsByTransactionType")
    public ResponseEntity<Object> getTransactionsByTransactionType(@RequestParam String transactionType) {
        try {
            // if transaction type is Outbound, convert it to OUTBOUND
            TransactionType enumTransactionType = null;
            if(transactionType.equalsIgnoreCase("Outbound")){
                enumTransactionType = TransactionType.OUTBOUND;
            } else if ( transactionType.equalsIgnoreCase("Inbound"))
            {
                enumTransactionType = TransactionType.INBOUND;
            }
            return ResponseEntity.ok(transactionService.getTransactionsByTransactionType(enumTransactionType));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getTransactionsByDate")
    public ResponseEntity<Object> getTransactionsByDate(@RequestParam String date) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionsByDate(date));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getTransactionsByDateRange")
    public ResponseEntity<Object> getTransactionsByDateRange(@RequestParam String startDate,
                                                             @RequestParam String endDate) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionsByDateRange(startDate, endDate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getTransactionsByMaterialIdAndDateRange")
    public ResponseEntity<Object> getTransactionsByMaterialIdAndDateRange(@RequestParam int materialId,
                                                                          @RequestParam String startDate,
                                                                          @RequestParam String endDate) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionsByMaterialIdAndDateRange(materialId,
                    startDate, endDate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
