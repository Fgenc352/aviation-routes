package com.aviation.routes.dto;

import com.aviation.routes.entity.TransportationType;
import jakarta.validation.constraints.*;
import java.util.Set;

public class TransportationDTO {
    private Long id;
    @NotBlank(message="Origin code is required")
    private String originCode;

    @NotBlank(message="Destination code is required")
    private String destinationCode;

    @NotNull(message="Type is required")
    private TransportationType transportationType;

    @NotEmpty(message="At least one operating day is required")
    private Set<@Min(value=1, message="Days must be between 1 and 7")
    @Max(value=7, message="Days must be between 1 and 7")
            Integer> operatingDays;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getOriginCode() {
        return originCode;
    }
    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }
    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public TransportationType getTransportationType() {
        return transportationType;
    }
    public void setTransportationType(TransportationType transportationType) {
        this.transportationType = transportationType;
    }

    public Set<Integer> getOperatingDays() {
        return operatingDays;
    }
    public void setOperatingDays(Set<Integer> operatingDays) {
        this.operatingDays = operatingDays;
    }
}
