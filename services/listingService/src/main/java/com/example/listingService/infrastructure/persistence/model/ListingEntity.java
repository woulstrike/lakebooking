package com.example.listingService.infrastructure.persistence.model;

import com.example.listingService.domain.enums.Status;
import com.example.listingService.domain.valueObject.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "listings")
public class ListingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Long price;

    @ElementCollection()
    @CollectionTable(name = "listing_photos", joinColumns = @JoinColumn(name = "listing_id"))
    @Column(name = "photo_url")
    private List<String> photos;

    @Embedded
    @Column(nullable = false)
    private LocationEmbeddable location;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "ban_reason")
    private String banReason;

    @Column(name = "banned_at")
    private LocalDateTime bannedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
