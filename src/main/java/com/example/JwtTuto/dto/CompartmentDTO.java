package com.example.JwtTuto.dto;

import com.example.JwtTuto.constant.CompartimentType;

import lombok.Data;

@Data
public class CompartmentDTO {
    private String name; // Assuming 'name' is the name of the compartment
    private String compartmentCode; // Assuming 'compartmentCode' is the code of the compartment
    private CompartimentType compartimentType; //This can be an enum of compartment types suc
    private String description;
    private int capacity;
}
