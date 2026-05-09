package com.example.userService.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserBanDTO {
    @NotBlank(message = "Choose id user for ban.")
    private Long id;

    @NotBlank(message = "Ban reason can't be empty.")
    private String banReason;
}
