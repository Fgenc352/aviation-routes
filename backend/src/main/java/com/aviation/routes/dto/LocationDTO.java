package com.aviation.routes.dto;

import jakarta.validation.constraints.*;

public class LocationDTO {
    private Long   id;
    @NotBlank(message="Name must not be empty")
    private String name;

    @NotBlank(message="Country must not be empty")
    private String country;

    @NotBlank(message="City must not be empty")
    private String city;

    @NotBlank(message="Code must not be empty")
    @Size(max=10, message="Code must be at most 10 characters")
    private String locationCode;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getLocationCode() { return locationCode; }
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
}
