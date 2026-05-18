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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserController {
    private final IUserService service;

    @GetMapping("/admin/users")
    public ResponseEntity<?> getAllUsers(@RequestHeader("X-User-Role") String roleHeader) {
        UserRole role = UserRole.valueOf(roleHeader);

        if (!role.equals(UserRole.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden.");
        }

        List<UserResponseDTO> allUsers = service.findAllUsers();

        return ResponseEntity.ok().body(allUsers);
    }


    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id, @RequestHeader("X-User-Role")  String roleHeader, @RequestHeader("X-User-Email") String emailHeader) {
        UserRole role = UserRole.valueOf(roleHeader);

        if (role.equals(UserRole.ADMIN)) {
            UserResponseDTO user = service.findUserById(id);
            return ResponseEntity.ok().body(user);
        }

        UserResponseDTO currentUser = service.findUserByEmail(emailHeader);
        if (!currentUser.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden.");
        }

        return ResponseEntity.ok().body(currentUser);
    }


    @GetMapping("/profile/email/")
    public ResponseEntity<?> getUserByEmail(@RequestParam(required = false) String email,  @RequestHeader("X-User-Role") String roleHeader,  @RequestHeader("X-User-Email") String emailHeader) {
        UserRole role = UserRole.valueOf(roleHeader);
        if (role.equals(UserRole.ADMIN)) {
            UserResponseDTO user = service.findUserByEmail(email);
            return ResponseEntity.ok().body(user);
        }

        UserResponseDTO user = service.findUserByEmail(emailHeader);
        return ResponseEntity.ok().body(user);
    }


    @PostMapping("/profile/activate/{email}")
    public ResponseEntity<?> activateUser(@PathVariable String email, @RequestHeader("X-User-Role")  String roleHeader, @RequestHeader("X-User-Email") String emailHeader) {
        UserRole role = UserRole.valueOf(roleHeader);
        if (role.equals(UserRole.ADMIN)) {
            service.activateUser(email);
            return ResponseEntity.ok().body("Activated");
        }

        if (!email.equals(emailHeader)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden.");
        }
        service.activateUser(email);
        return ResponseEntity.ok().body("Activated");
    }


    @DeleteMapping("/profile/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestHeader("X-User-Role")  String roleHeader, @RequestHeader("X-User-Email") String emailHeader) {
        UserRole role = UserRole.valueOf(roleHeader);
        if (role.equals(UserRole.ADMIN)) {
            service.deleteUser(id);
            return ResponseEntity.ok().body("Deleted");
        }
        UserResponseDTO user = service.findUserByEmail(emailHeader);

        if (!user.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden.");
        }

        service.deleteUser(id);
        return ResponseEntity.ok().body("Deleted");
    }


    @PatchMapping("/profile")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO updateDTO, @RequestHeader("X-User-Email") String emailHeader) {
        UserResponseDTO currentUser = service.findUserByEmail(emailHeader);

        UserResponseDTO updatedUser = service.updateUser(currentUser.getId(), updateDTO);

        return ResponseEntity.ok().body(updatedUser);
    }


    @PatchMapping("/profile/password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody UserUpdatePasswordDTO passwordDTO, @RequestHeader("X-User-Email")  String emailHeader) {
        UserResponseDTO currentUser = service.findUserByEmail(emailHeader);

        if (!currentUser.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden.");
        }

        UserResponseDTO updatedUser = service.updatePassword(currentUser.getId(), passwordDTO);
        return ResponseEntity.ok().body(updatedUser);
    }


    @PostMapping("/profile/role/{userId}")
    public ResponseEntity<?> updateRole(@PathVariable Long userId, @RequestBody UserRoleDTO userRole, @RequestHeader("X-User-Role")  String roleHeader, @RequestHeader("X-User-Email") String emailHeader) {
        UserRole role = UserRole.valueOf(roleHeader);
//        if (role.equals(UserRole.ADMIN)) {
            UserResponseDTO user = service.updateRole(userId, userRole);
            return  ResponseEntity.ok().body(user);
//        }
//        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden.");
    }

//    @PostMapping("/users/ban/{id}")
//    public ResponseEntity<?> banUser(@PathVariable Long id, @RequestBody UserBanDTO banDTO) {
//        service.banUser(id, banDTO);
//
//        return ResponseEntity.ok().body("User" + id + " has been banned");
//    }
}
