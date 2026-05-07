package com.example.userService.domain.enums;

public enum UserStatus {
    PENDING,
    ACTIVE,
    DELETED,
    BANNED;

    public boolean canChangeStatus(UserStatus newStatus) {
        return switch (this) {
            case PENDING -> newStatus == PENDING || newStatus == ACTIVE;
            case ACTIVE -> newStatus == DELETED || newStatus == BANNED;
            case BANNED -> newStatus == DELETED;
            case DELETED -> false;
        };
    }
}
