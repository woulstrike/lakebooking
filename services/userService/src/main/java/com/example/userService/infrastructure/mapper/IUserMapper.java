package com.example.userService.infrastructure.mapper;

import com.example.userService.api.dto.UserResponseDTO;
import com.example.userService.domain.entities.User;
import com.example.userService.domain.valueObjects.Password;
import com.example.userService.infrastructure.persistence.models.UserEntity;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = false))
public interface IUserMapper {

    User toDomain(UserEntity entity);

    UserEntity toEntity(User user);

    UserResponseDTO toResponseDTO(User user);

    default String mapPasswordToString(Password password) {
        return password.getHashedPassword();
    }

    @ObjectFactory
    default User createUser(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUserName(),
                entity.getEmail(),
                Password.fromHash(entity.getPassword()),
                entity.getRole(),
                entity.getStatus(),
                entity.getBanReason(),
                entity.getBanDate(),
                entity.getCreatedAt(),
                entity.getActivatedAt()

        );
    }

    @ObjectFactory
    default UserEntity createUserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setUserName(user.getUserName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword().getHashedPassword());
        userEntity.setRole(user.getRole());
        userEntity.setStatus(user.getStatus());
        userEntity.setBanReason(user.getBanReason());
        userEntity.setActivatedAt(user.getActivatedAt());
        userEntity.setBanDate(user.getBanDate());
        userEntity.setCreatedAt(user.getCreatedAt());

        return userEntity;
    }
}
