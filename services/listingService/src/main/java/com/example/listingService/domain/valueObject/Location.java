package com.example.listingService.domain.valueObject;

import com.example.listingService.domain.exception.DomainException;
import lombok.Getter;

@Getter
public class Location {
    private String city;

    private String state;

    private String address;

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

    public void changeCity(String city) {
        if (city == null || city.isBlank()) {
            throw new DomainException("City is empty.");
        }

        this.city = city;
    }

    public void changeState(String state) {
        if (state == null || state.isBlank()) {
            throw new DomainException("State is empty.");
        }

        this.state = state;
    }

    public void changeAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new DomainException("Address is empty.");
        }

        this.address = address;
    }
}
