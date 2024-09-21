package com.example.JwtTuto.entity;

import com.example.JwtTuto.constant.CompartimentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "compartment")
public class Compartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name; // Assuming 'name' is the name of the compartment
    private String compartmentCode; // Assuming 'compartmentCode' is the code of the compartment
    private CompartimentType compartimentType; //This can be an enum of compartment types suc
    @Lob
    private String description;
    private Boolean isAvailable;
    private int capacity;

    @OneToMany(mappedBy = "compartment")
    @JsonBackReference
    private List <Material> materials;

}
