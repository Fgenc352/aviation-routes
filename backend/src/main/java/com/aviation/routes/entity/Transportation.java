package com.aviation.routes.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transportations")
public class Transportation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(
            name                = "origin_location",
            referencedColumnName = "location_code",
            nullable = false,
            foreignKey = @ForeignKey(
                    name="fk_origin_location",
                    foreignKeyDefinition =
                            "FOREIGN KEY(origin_location) REFERENCES locations(location_code) ON UPDATE CASCADE"
            )
    )
    private Location originLocation;

    @ManyToOne(optional = false)
    @JoinColumn(
            name                = "destination_location",
            referencedColumnName = "location_code",
            nullable = false,
            foreignKey = @ForeignKey(
                    name="fk_destination_location",
                    foreignKeyDefinition =
                            "FOREIGN KEY(destination_location) REFERENCES locations(location_code) ON UPDATE CASCADE"
            )
    )
    private Location destinationLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "transportation_type", nullable = false)
    private TransportationType transportationType;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name        = "transportation_operating_days",
            joinColumns = @JoinColumn(name = "transportation_id")
    )
    @Column(name = "day_of_week", nullable = false)
    private Set<Integer> operatingDays = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Location getOriginLocation() { return originLocation; }
    public void setOriginLocation(Location originLocation) {
        this.originLocation = originLocation;
    }

    public Location getDestinationLocation() { return destinationLocation; }
    public void setDestinationLocation(Location destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public TransportationType getTransportationType() {
        return transportationType;
    }
    public void setTransportationType(
            TransportationType transportationType) {
        this.transportationType = transportationType;
    }

    public Set<Integer> getOperatingDays() { return operatingDays; }
    public void setOperatingDays(Set<Integer> operatingDays) {
        this.operatingDays = operatingDays;
    }
}
