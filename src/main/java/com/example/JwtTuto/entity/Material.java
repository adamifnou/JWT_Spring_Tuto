package com.example.JwtTuto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "material")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String code;
    private String description;
    // Varchar(10) is a good choice for unit of measure
    @Column(name = "unit_of_measure", length = 20)
    private String unitOfMeasure;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "compartment_id", referencedColumnName = "id")
    @JsonBackReference
    private Compartment compartment;
}
