package com.example.JwtTuto.entity;

import com.example.JwtTuto.constant.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "material_transaction")
public class MaterialTransaction {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @Enumerated(EnumType.STRING)
        private TransactionType transactionType;
        private LocalDateTime transactionDate;
        private int quantity;
        private String description;

        @OneToOne
        @JoinColumn(name = "material_id", referencedColumnName = "id")
        private Material material;

        @ManyToOne
        @JoinColumn(name = "executor_id", referencedColumnName = "id")
        private UserInfo executor;

}
