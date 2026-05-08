package com.example.userService.application.services;

import com.example.userService.api.dto.*;
import com.example.userService.application.exceptions.LoginException;
import com.example.userService.application.exceptions.RegistrationException;
import com.example.userService.application.exceptions.UpdateException;
import com.example.userService.application.interfaces.IUserService;
import com.example.userService.domain.entities.User;
import com.example.userService.domain.enums.UserRole;
import com.example.userService.domain.enums.UserStatus;
import com.example.userService.domain.repository.IUserRepository;
import com.example.userService.domain.valueObjects.Password;
import com.example.userService.infrastructure.mapper.IUserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IUserMapper mapper;

    @Override
    @Transactional
    public UserResponseDTO registerUser(UserRegistrationDTO registrationDTO) {
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new RegistrationException("Email already exists.");
        }

        User user = new User(
                registrationDTO.getUserName(),
                registrationDTO.getEmail(),
                Password.fromText(registrationDTO.getPassword()),
                UserRole.USER
        );

        User savedUser = userRepository.save(user);

        log.info("User registered successfully with id: {}.", savedUser.getId());
        return mapper.toResponseDTO(savedUser);
    }


    @Override
    public UserResponseDTO loginUser(UserLoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new LoginException("Email not found."));

        if (!user.getPassword().equals(Password.fromText(loginDTO.getPassword()))) {
            throw new LoginException("Invalid email or password.");
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new LoginException("Activate your account for login.");
        }

        log.info("User logged in successfully with id: {}.", user.getId());

        return mapper.toResponseDTO(user);
    }


    @Override
    public List<UserResponseDTO> findAllUsers() {
        List<User> allUsers = userRepository.findAll();
        log.info("All users found successfully.");

        return allUsers.stream().map(mapper::toResponseDTO).toList();
    }


    @Override
    @Transactional
    public void activateUser(String email) {
        if (!userRepository.existsByEmail(email) || email.isBlank()) {
            throw new RuntimeException("Email does not exist.");
        }

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Error to activate user."));

        user.activateUser();
        userRepository.save(user);

        log.info("Email successfully activated: {}.", email);
    }


    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Error to delete user."));
        user.delete();

        userRepository.save(user);
        log.info("User with id {} deleted successfully.", id);
    }


    @Override
    public UserResponseDTO findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found."));
        log.info("User with id {} found successfully.", id);

        return mapper.toResponseDTO(user);
    }


    @Override
    public UserResponseDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User with email " + email + " not found."));
        log.info("User with email {} found successfully.", email);

        return mapper.toResponseDTO(user);
    }

    @Override
    @Transactional
    public UserResponseDTO updatePassword(Long id, UserUpdatePasswordDTO userUpdatePasswordDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found."));

        Password currentPassword = Password.fromText(userUpdatePasswordDTO.getCurrentPassword());
        Password newPassword = Password.fromText(userUpdatePasswordDTO.getNewPassword());

        user.changePassword(currentPassword, newPassword);

        userRepository.save(user);
        log.info("User id {} successfully updated password.", id);

        return mapper.toResponseDTO(user);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UpdateException("Can't find user by id for update."));
        log.info("Update for {} user.", user.getUserName());

        switch (userUpdateDTO.getField()) {
            case "newUserName": {
                userRepository.findByUserName(userUpdateDTO.getNewUserName())
                        .ifPresent(existingUser -> {
                            if (!existingUser.getId().equals(id)) {
                                throw new UpdateException("Username already exists.");
                            }
                        });

                user.changeUserName(userUpdateDTO.getNewUserName());
                break;
            }
            case "newEmail": {
                userRepository.findByEmail(userUpdateDTO.getNewEmail())
                        .ifPresent(existingEmail -> {
                            if (!existingEmail.getId().equals(id)) {
                                throw new UpdateException("Email already exists.");
                            }
                        });

                user.changeEmail(userUpdateDTO.getNewEmail());
                break;
            }
        }

        userRepository.save(user);
        log.info("User with id {} updated successfully.", id);

        return mapper.toResponseDTO(user);
    }
}
