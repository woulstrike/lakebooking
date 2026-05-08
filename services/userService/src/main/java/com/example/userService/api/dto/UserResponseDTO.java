package com.example.userService.api.dto;

import com.example.userService.domain.enums.UserRole;
import com.example.userService.domain.enums.UserStatus;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString(onlyExplicitlyIncluded = true)
public class UserResponseDTO {
    private Long id;

    private String userName;

    @ToString.Include
    private String email;

    private UserRole roles;

    private UserStatus status;

    private LocalDateTime createdAt;
}
