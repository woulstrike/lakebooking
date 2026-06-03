package com.example.listingService.domain.valueObject;

import com.example.listingService.domain.exception.DomainException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Location {
    private final String city;

    private final String state;

    private final String address;

    public Location(String city, String state, String address) {
        if (city == null || city.isBlank()) {
            throw new DomainException("City is required.");
        }

        if (state == null || state.isBlank()) {
            throw new DomainException("State is required.");
        }

        if (address == null || address.isBlank()) {
            throw new DomainException("Address is required.");
        }

        this.city = city;
        this.state = state;
        this.address = address;
    }

    public Location withCity(String city) {
        if (city == null || city.isBlank()) {
            throw new DomainException("City is empty.");
        }

        return new Location(city, this.state, this.address);
    }

    public Location withState(String state) {
        if (state == null || state.isBlank()) {
            throw new DomainException("State is empty.");
        }

        return new Location(this.city, state, this.address);
    }

    public Location withAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new DomainException("Address is empty.");
        }

        return new Location(this.city, this.state, address);
    }
}
