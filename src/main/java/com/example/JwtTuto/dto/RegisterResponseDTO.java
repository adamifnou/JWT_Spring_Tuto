package com.example.JwtTuto.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterResponseDTO {
    private String message;

    public RegisterResponseDTO(String message) {
        this.message = message;
    }

}
