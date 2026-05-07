package com.example.userService.api.dto;

public record UserDTO(
        String userName,
        String email,
        String password
) {
}
