package com.example.userService.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdatePasswordDTO {
    @NotBlank(message = "Current password is required.")
    private String currentPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "Minimal size for new password is 8 characters.")
    private String newPassword;
}
