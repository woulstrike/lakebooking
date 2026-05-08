package com.example.userService.infrastructure.persistence.repositories;

import com.example.userService.domain.repository.IUserRepository;
import com.example.userService.domain.entities.User;
import com.example.userService.infrastructure.mapper.IUserMapper;
import com.example.userService.infrastructure.persistence.models.UserEntity;
import com.example.userService.infrastructure.persistence.interfaces.IUserRepositoryJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepository implements IUserRepository {
    private final IUserRepositoryJPA jpaRepository;
    private final IUserMapper mapper;

    @Override
    public User save(User user) {
        // map domain to entity
        UserEntity userEntity = mapper.toEntity(user);
        //save user entity
        UserEntity savedUserEntity = jpaRepository.save(userEntity);
        //return saved user entity as domain
        return mapper.toDomain(savedUserEntity);
    }


    @Override
    public Optional<User> findById(Long id) {
        //find by id and return domain
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }


    @Override
    public Optional<User> findByEmail(String email) {
        //find by email and return domain
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }


    @Override
    public Optional<User> findByUserName(String username) {
        return jpaRepository.findByUserName(username)
                .map(mapper::toDomain);
    }


    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }


    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }


    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUserName(username);
    }


    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

}
