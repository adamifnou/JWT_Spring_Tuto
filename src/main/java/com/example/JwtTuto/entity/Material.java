package com.example.JwtTuto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "material")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String code;
    private String description;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "compartment_id", referencedColumnName = "id")
    @JsonBackReference
    private Compartment compartment;

    public int getCompartmentId() {
        return compartment != null ? compartment.getId() : 0;
    }
    public String getCompartmentName() {
        return compartment != null ? compartment.getName() : "";
    }
}
