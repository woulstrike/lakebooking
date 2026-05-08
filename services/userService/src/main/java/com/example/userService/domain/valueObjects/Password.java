package com.example.userService.domain.valueObjects;

import com.example.userService.domain.exception.DomainException;
import lombok.*;

@Value
public class Password {
    String hashedPassword;

    public static Password fromText(String password){
        if (password == null ||password.isBlank()) {
            throw new DomainException("Password can't be empty.");
        }

        if (password.length() < 8) {
            throw new DomainException("Password must be at least 8 characters long.");
        }

        return new Password(password);
    }

    public static Password fromHash(String hashedPassword){
        if (hashedPassword == null ||hashedPassword.isBlank()) {
            throw new DomainException("Password can't be empty.");
        }

        return new Password(hashedPassword);
    }
}
