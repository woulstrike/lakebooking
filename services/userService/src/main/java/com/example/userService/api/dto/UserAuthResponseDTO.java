package com.example.userService.api.dto;

import lombok.Data;

@Data
public class UserAuthResponseDTO {
    private String token;
    private UserResponseDTO user;
}
