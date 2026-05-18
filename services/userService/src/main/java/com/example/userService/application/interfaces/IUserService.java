package com.example.userService.application.interfaces;

import com.example.userService.api.dto.*;
import com.example.userService.domain.enums.UserRole;
import com.example.userService.domain.enums.UserStatus;

import java.util.List;

public interface IUserService {
    UserResponseDTO registerUser(UserRegistrationDTO registrationDTO);

    UserResponseDTO loginUser(UserLoginDTO loginDTO);

    List<UserResponseDTO> findAllUsers();

    void activateUser(String email);

    void deleteUser(Long id);

    UserResponseDTO findUserById(Long id);

    UserResponseDTO findUserByEmail(String email);

    UserResponseDTO updatePassword(Long id, UserUpdatePasswordDTO userUpdatePasswordDTO);

    UserResponseDTO updateUser(Long id, UserUpdateDTO userUpdateDTO);

    UserResponseDTO updateRole(Long userId, UserRoleDTO role);
//    void banUser(Long id, UserBanDTO banDTO);
}
