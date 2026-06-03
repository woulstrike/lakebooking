package com.example.listingService.infrastructure.mapper;

import com.example.listingService.domain.entity.Listing;
import com.example.listingService.domain.valueObject.Location;
import com.example.listingService.infrastructure.persistence.model.ListingEntity;
import com.example.listingService.infrastructure.persistence.model.LocationEmbeddable;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = false))
public interface IListingMapper {
    Listing toListing(ListingEntity entity);

    ListingEntity toListingEntity(Listing listing);

    @ObjectFactory
    default Listing createListing(ListingEntity entity) {
        LocationEmbeddable locationEmbeddable = entity.getLocation();

        if (locationEmbeddable == null) {
            throw new IllegalStateException("Location cannot be null or empty.");
        }

        Location location = new Location(
                locationEmbeddable.getCity(),
                locationEmbeddable.getState(),
                locationEmbeddable.getAddress()
        );

        return new Listing(
                entity.getId(),
                entity.getOwnerId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getPhotos(),
                location,
                entity.getStatus(),
                entity.getBanReason(),
                entity.getBannedAt(),
                entity.getCreatedAt(),
                entity.getDeletedAt(),
                entity.getUpdatedAt()
        );
    }

    @ObjectFactory
    default ListingEntity createListingEntity(Listing listing){
        ListingEntity listingEntity = new ListingEntity();
        listingEntity.setId(listing.getId());
        listingEntity.setOwnerId(listing.getOwnerId());
        listingEntity.setTitle(listing.getTitle());
        listingEntity.setDescription(listing.getDescription());
        listingEntity.setPrice(listing.getPrice());
        listingEntity.setPhotos(listing.getPhotos());
        listingEntity.setLocation(new LocationEmbeddable(
                listing.getLocation().getCity(),
                listing.getLocation().getState(),
                listing.getLocation().getAddress()
        ));
        listingEntity.setStatus(listing.getStatus());
        listingEntity.setBanReason(listing.getBanReason());
        listingEntity.setBannedAt(listing.getBannedAt());
        listingEntity.setCreatedAt(listing.getCreatedAt());
        listingEntity.setDeletedAt(listing.getDeletedAt());
        listingEntity.setUpdatedAt(listing.getUpdatedAt());

        return listingEntity;
    };
}
