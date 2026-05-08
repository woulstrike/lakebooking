package com.example.userService.infrastructure.persistence.interfaces;

import com.example.userService.infrastructure.persistence.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepositoryJPA extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUserName(String username);

    boolean existsByEmail(String email);

    boolean existsByUserName(String username);
}
