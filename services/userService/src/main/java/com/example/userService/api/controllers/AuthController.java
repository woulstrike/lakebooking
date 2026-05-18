package com.example.userService.api.controllers;

import com.example.userService.api.dto.UserAuthResponseDTO;
import com.example.userService.api.dto.UserLoginDTO;
import com.example.userService.api.dto.UserRegistrationDTO;
import com.example.userService.api.dto.UserResponseDTO;
import com.example.userService.application.interfaces.IUserService;
import com.example.userService.application.services.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user-service/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IUserService userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginDTO) {
        UserResponseDTO user = userService.loginUser(loginDTO);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",  user.getRole());
        String token = jwtService.generateToken(claims,  user.getEmail());

        UserAuthResponseDTO userAuthResponseDTO = new UserAuthResponseDTO();
        userAuthResponseDTO.setToken(token);
        userAuthResponseDTO.setUser(user);

        return ResponseEntity.ok().body(userAuthResponseDTO);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        UserResponseDTO user = userService.registerUser(registrationDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
