package com.example.userService.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    @NotBlank(message = "Choose the field for changes.")
    private String field;

    @Size(min = 4, max = 30, message = "Minimal size for user name - 4 characters")
    private String newUserName;

    @Email(message = "Invalid email.")
    private String newEmail;
}
