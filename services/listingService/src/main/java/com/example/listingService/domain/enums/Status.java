package com.example.listingService.domain.enums;

public enum Status {
    DRAFT,
    PUBLISHED,
    ARCHIVED,
    DELETED,
    BLOCKED;

    public boolean canChangeStatus(Status newStatus) {
        return switch (this) {
            case DRAFT -> newStatus == Status.DRAFT || newStatus == PUBLISHED || newStatus == ARCHIVED;
            case PUBLISHED -> newStatus == Status.PUBLISHED  || newStatus == ARCHIVED || newStatus == DRAFT;
            case ARCHIVED -> newStatus == Status.ARCHIVED || newStatus == PUBLISHED;
            case BLOCKED, DELETED -> false;
        };
    }
}
