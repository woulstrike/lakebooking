package com.example.listingService.infrastructure.persistence.interfaces;

import com.example.listingService.infrastructure.persistence.model.ListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IListingRepositoryJPA extends JpaRepository<ListingEntity, Long> {
    Optional<ListingEntity> findByTitle(String title);

    boolean existsByTitle(String title);
}
