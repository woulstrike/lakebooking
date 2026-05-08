package com.example.userService.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {
    @Email(message = "Invalid email.")
    @NotBlank(message = "Email is required to login.")
    private String email;

    @NotBlank(message = "Password is required to login.")
    private String password;
}
