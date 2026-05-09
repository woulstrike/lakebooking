package com.example.userService.api.controllers;

import com.example.userService.api.dto.UserLoginDTO;
import com.example.userService.api.dto.UserRegistrationDTO;
import com.example.userService.api.dto.UserResponseDTO;
import com.example.userService.application.interfaces.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginDTO) {
        UserResponseDTO user = userService.loginUser(loginDTO);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        UserResponseDTO user = userService.registerUser(registrationDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
