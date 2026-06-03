package com.example.listingService.infrastructure.persistence.repository;

import com.example.listingService.domain.entity.Listing;
import com.example.listingService.domain.repository.IListingRepository;
import com.example.listingService.infrastructure.mapper.IListingMapper;
import com.example.listingService.infrastructure.persistence.interfaces.IListingRepositoryJPA;
import com.example.listingService.infrastructure.persistence.model.ListingEntity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ListingRepository implements IListingRepository {
    private final IListingRepositoryJPA jpaRepository;
    private final IListingMapper mapper;

    @Override
    public Listing save(Listing listing) {
        ListingEntity listingEntity = mapper.toListingEntity(listing);
        ListingEntity savedEntity = jpaRepository.save(listingEntity);

        return mapper.toListing(savedEntity);
    }

    @Override
    public Optional<Listing> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toListing);
    }

    @Override
    public List<Listing> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toListing)
                .toList();
    }

    @Override
    public Optional<Listing> findByTitle(String title) {
        return jpaRepository.findByTitle(title)
                .map(mapper::toListing);
    }

    @Override
    public void softDelete(Long id) {
        ListingEntity listingEntity = jpaRepository.findById(id).orElseThrow();
        Listing listing = mapper.toListing(listingEntity);
        listing.deleteListing();

        jpaRepository.save(mapper.toListingEntity(listing));
    }
}
