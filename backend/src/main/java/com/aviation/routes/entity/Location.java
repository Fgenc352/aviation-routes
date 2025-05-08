package com.aviation.routes.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "locations",
        uniqueConstraints = @UniqueConstraint(columnNames = "location_code")
)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String country;
    private String city;

    @Column(name = "location_code", nullable = false, length = 10)
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
