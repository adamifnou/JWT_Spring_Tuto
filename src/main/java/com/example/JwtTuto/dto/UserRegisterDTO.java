package com.example.JwtTuto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserRegisterDTO {
    private String name;
    private String email;
    private String password;



}
