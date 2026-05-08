package com.example.userService.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDTO {
    @NotBlank(message = "User name is required.")
    @Size(min = 4, max = 30, message = "Minimal size for user name - 4 characters.")
    private String userName;

    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Minimal size for password - 8 characters.")
    private String password;
}
