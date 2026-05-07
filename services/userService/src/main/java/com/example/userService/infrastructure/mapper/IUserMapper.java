package com.example.userService.infrastructure.mapper;

import com.example.userService.domain.entities.User;
import com.example.userService.domain.valueObjects.Password;
import com.example.userService.infrastructure.persistence.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    @Mapping(target = "password", expression = "java(mapPasswordToDomain(entity.getPassword()))")
    User toDomain(UserEntity entity);

    @Mapping(target = "password", expression = "java(mapPasswordToString(entity.getPassword()))")
    UserEntity toEntity(User user);

    default Password mapPasswordToDomain(String password) {
        return Password.fromHash(password);
    }

    default String mapPasswordToString(Password password) {
        return password.getHashedPassword();
    }
}
