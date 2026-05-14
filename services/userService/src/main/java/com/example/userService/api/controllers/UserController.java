package com.example.userService.api.controllers;

import com.example.userService.api.dto.UserResponseDTO;
import com.example.userService.api.dto.UserRoleDTO;
import com.example.userService.api.dto.UserUpdateDTO;
import com.example.userService.api.dto.UserUpdatePasswordDTO;
import com.example.userService.application.interfaces.IUserService;
import com.example.userService.domain.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserController {
    private final IUserService service;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<UserResponseDTO> allUsers = service.findAllUsers();

        return ResponseEntity.ok().body(allUsers);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        UserResponseDTO user = service.findUserById(id);

        return ResponseEntity.ok().body(user);

    }


    @GetMapping("/users/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        UserResponseDTO user = service.findUserByEmail(email);

        return ResponseEntity.ok().body(user);

    }


    @PostMapping("/users/activate/{email}")
    public ResponseEntity<?> activateUser(@PathVariable String email) {
        service.activateUser(email);

        return ResponseEntity.ok().body("Activated");

    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);

        return ResponseEntity.ok().body("Deleted");

    }


    @PatchMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO updateDTO) {
        UserResponseDTO user = service.updateUser(id, updateDTO);

        return ResponseEntity.ok().body(user);
    }


    @PatchMapping("/users/password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody UserUpdatePasswordDTO passwordDTO) {
        UserResponseDTO user = service.updatePassword(id, passwordDTO);

        return ResponseEntity.ok().body(user);
    }


    @PostMapping("/users/role/{adminId}/{userId}")
    public ResponseEntity<?> updateRole(@PathVariable Long adminId, @PathVariable Long userId, @RequestBody UserRoleDTO userRole) {
        UserResponseDTO user = service.updateRole(adminId, userId, userRole);

        return  ResponseEntity.ok().body(user);
    }

//    @PostMapping("/users/ban/{id}")
//    public ResponseEntity<?> banUser(@PathVariable Long id, @RequestBody UserBanDTO banDTO) {
//        service.banUser(id, banDTO);
//
//        return ResponseEntity.ok().body("User" + id + " has been banned");
//    }
}
