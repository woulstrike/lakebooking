package com.example.userService.domain.repository;

import com.example.userService.domain.entities.User;

import java.util.List;
import java.util.Optional;


public interface IUserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String username);

    void delete(Long id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    List<User> findAll();
}
