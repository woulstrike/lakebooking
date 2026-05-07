package com.example.userService.domain.repository;

import com.example.userService.domain.entities.User;

import java.util.Optional;


public interface IUserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    void delete(User user);

    boolean existsByEmail(String email);
}
