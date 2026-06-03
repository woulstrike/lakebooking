package com.example.listingService.domain.entity;

import com.example.listingService.domain.enums.Status;
import com.example.listingService.domain.exception.DomainException;
import com.example.listingService.domain.valueObject.Location;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Listing {
    private final Long id;

    private final Long ownerId;

    private String title;

    private String description;

    private Long price;

    private List<String> photos;

    private Location location;

    private Status status;

    private String banReason;

    private LocalDateTime bannedAt;

    private final LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    private LocalDateTime updatedAt;

    //constructor for creating listing
    public Listing(Long id, Long ownerId, String title, String description, Long price, List<String> photos, Location location) {
        if (ownerId == null) {
            throw new DomainException("OwnerId is required.");
        }

        if (title == null || title.isBlank()) {
            throw new DomainException("Title is required.");
        }

        if (description == null || description.isBlank()) {
            throw new DomainException("Description is required.");
        }

        if (price == null || price <= 0) {
            throw new DomainException("Price is required.");
        }

        if (photos == null || photos.isEmpty()) {
            throw new DomainException("Photos is required.");
        }

        if (location == null) {
            throw new DomainException("Location is required.");
        }

        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.photos = photos;
        this.location = location;
        this.status = Status.DRAFT;
        this.createdAt = LocalDateTime.now();
    }

    //constructor for mapper
    public Listing(Long id, Long ownerId, String title, String description, Long price, List<String> photos, Location location, Status status,  String banReason, LocalDateTime bannedAt, LocalDateTime createdAt, LocalDateTime deletedAt,  LocalDateTime updatedAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.photos = photos;
        this.location = location;
        this.status = status;
        this.banReason = banReason;
        this.bannedAt = bannedAt;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.updatedAt = updatedAt;
    }

    public void changeTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new DomainException("Title is null.");
        }

        if (title.length() > 150) {
            throw new DomainException("Title cannot exceed 150 characters.");
        }

        ensureEditable();

        this.updatedAt = LocalDateTime.now();
        this.title = title;
    }


    public void changeDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new DomainException("Description is null.");
        }

        if (description.length() > 1500) {
            throw new DomainException("Description cannot exceed 1500 characters.");
        }

        ensureEditable();

        this.updatedAt = LocalDateTime.now();
        this.description = description;
    }


    public void changePrice(Long price) {
        if (price == null || price <= 0) {
            throw new DomainException("Wrong price.");
        }

        if (this.price.equals(price)) {
            throw  new DomainException("The listing price cannot be the same.");
        }

        ensureEditable();

        this.updatedAt = LocalDateTime.now();
        this.price = price;
    }


    public void changePhotos(List<String> photos) {
        if (photos == null || photos.isEmpty()) {
            throw new DomainException("Photos is null.");
        }

        ensureEditable();

        this.updatedAt = LocalDateTime.now();
        this.photos = photos;
    }

    public void changeLocation(Location location) {
        if (location == null) {
            throw new DomainException("Location is null.");
        }

        ensureEditable();

        this.updatedAt = LocalDateTime.now();
        this.location = location;
    }

    public void changeStatus(Status status) {
        if (status == null) {
            throw new DomainException("Status is null.");
        }

        if (!this.status.canChangeStatus(status)) {
            throw  new DomainException("The listing status cannot change the status.");
        }

        ensureEditable();

        this.updatedAt = LocalDateTime.now();
        this.status = status;
    }


    public void deleteListing() {
        if (this.status == Status.DELETED) {
            throw new DomainException("Cannot delete the deleted listing.");
        }

        this.status = Status.DELETED;
        this.deletedAt = LocalDateTime.now();
    }


    public void banListing(String banReason) {
        if (banReason == null) {
            throw new DomainException("Ban reason cannot be null.");
        }

        if (!this.status.equals(Status.PUBLISHED)) {
            throw  new DomainException("The listing should be published for ban.");
        }

        ensureEditable();

        this.status = Status.BLOCKED;
        this.banReason = banReason;
        this.bannedAt = LocalDateTime.now();
    }

    private void ensureEditable() {
        if (this.status == Status.DELETED) {
            throw new DomainException("Cannot change the deleted listing.");
        }

        if (this.status == Status.BLOCKED) {
            throw new DomainException("The listing has not available.");
        }
    }
}
