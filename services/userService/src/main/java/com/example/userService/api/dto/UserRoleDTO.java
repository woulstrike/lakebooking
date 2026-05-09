package com.example.userService.api.dto;

import com.example.userService.domain.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRoleDTO {
    @NotBlank(message = "New role can't be empty.")
    private UserRole role;
}
