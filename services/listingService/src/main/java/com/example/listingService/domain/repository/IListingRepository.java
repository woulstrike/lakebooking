package com.example.listingService.domain.repository;

import com.example.listingService.domain.entity.Listing;

import java.util.List;
import java.util.Optional;

public interface IListingRepository {
    Listing save(Listing listing);

    Optional<Listing> findById(Long id);

    List<Listing> findAll();

    Optional<Listing> findByTitle(String title);

    void delete(Long id);
}
