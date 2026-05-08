package com.example.userService.domain.entities;

import com.example.userService.domain.enums.UserRole;
import com.example.userService.domain.enums.UserStatus;
import com.example.userService.domain.exception.DomainException;
import com.example.userService.domain.valueObjects.Password;
import lombok.*;

import java.time.LocalDateTime;

@Getter
public class User {
    private Long id;

    private String userName;

    private String email;

    private Password password;

    private UserRole role;

    private UserStatus status;

    private String banReason;

    private LocalDateTime banDate;

    private LocalDateTime createdAt;

    private LocalDateTime activatedAt;

    //constructor for creating user
    public User(String userName, String email, Password password, UserRole role) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = UserStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    //constructor for database mapper
    public User(Long id, String userName, String email, Password password, UserRole role, UserStatus status, String banReason, LocalDateTime banDate, LocalDateTime createdAt, LocalDateTime activatedAt) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.banReason = banReason;
        this.banDate = banDate;
        this.createdAt = createdAt;
        this.activatedAt = activatedAt;
    }

    public void changeEmail(String newEmail){
        if (this.email.equals(newEmail)) {
            throw new DomainException("Email equals your current email.");
        }

        if (this.status != UserStatus.ACTIVE && this.status != UserStatus.PENDING) {
            throw new DomainException("Email can't be changed.");
        }

        this.email = newEmail;
    }


    public void changeUserName(String newUserName){
        if (this.userName.equals(newUserName)) {
            throw new DomainException("User name equals your current user name.");
        }

        if (this.status != UserStatus.ACTIVE && this.status != UserStatus.PENDING) {
            throw new DomainException("User name can't be changed.");
        }

        if (newUserName.length() < 4 || newUserName.length() > 30) {
            throw new DomainException("Username must be between 4 and 30 characters");
        }

        this.userName = newUserName;
    }


    public void changePassword(Password oldPassword, Password newPassword){
        if (this.status != UserStatus.ACTIVE && this.status != UserStatus.PENDING) {
            throw new DomainException("Password can't be changed.");
        }

        if (!this.password.equals(oldPassword)) {
            throw new DomainException("Current password incorrect.");
        }

        if (this.password.equals(newPassword)) {
            throw new DomainException("New password can't be equals to current password.");
        }

        this.password = newPassword;
    }


    public void activateUser() {
        if (this.status == UserStatus.BANNED) {
            throw new DomainException("Can't activate banned user.");
        }

        if (this.status == UserStatus.DELETED) {
            throw new DomainException("Can't activate deleted user.");
        }

        this.status = UserStatus.ACTIVE;
        this.activatedAt = LocalDateTime.now();
    }

    public void banUser(String reason) {
        if (this.status == UserStatus.BANNED) {
            throw new DomainException("User is already banned.");
        }

        if (this.status == UserStatus.DELETED) {
            throw new DomainException("Can't ban deleted user.");
        }

        if (reason == null) {
            throw new DomainException("Ban reason can't be null.");
        }

        this.status = UserStatus.BANNED;
        this.banReason = reason;
        this.banDate = LocalDateTime.now();
    }

    public boolean canBook() {
        if (this.status != UserStatus.ACTIVE) {
            return false;
        }

        if (this.role == UserRole.ADMIN) {
            return false;
        }

        return this.role != UserRole.MODERATOR;
    }

    public void delete() {
        if (this.status == UserStatus.DELETED) {
            throw new DomainException("Can't delete deleted user.");
        }

        this.status = UserStatus.DELETED;
    }
}
