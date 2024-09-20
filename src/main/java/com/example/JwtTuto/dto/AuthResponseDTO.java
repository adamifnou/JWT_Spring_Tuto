package com.example.JwtTuto.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private int id;
    private String name;
    private String email;


    public AuthResponseDTO(String token, int id, String name, String email) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
