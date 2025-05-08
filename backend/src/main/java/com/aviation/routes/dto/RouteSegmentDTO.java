package com.aviation.routes.dto;

import com.aviation.routes.entity.TransportationType;
import java.util.Set;

public class RouteSegmentDTO {
    private String originCode;
    private String destinationCode;
    private TransportationType transportationType;
    private Set<Integer> operatingDays;

    public String getOriginCode() { return originCode; }
    public void setOriginCode(String originCode) { this.originCode = originCode; }

    public String getDestinationCode() { return destinationCode; }
    public void setDestinationCode(String destinationCode) { this.destinationCode = destinationCode; }

    public TransportationType getTransportationType() { return transportationType; }
    public void setTransportationType(TransportationType transportationType) {
        this.transportationType = transportationType;
    }

    public Set<Integer> getOperatingDays() { return operatingDays; }
    public void setOperatingDays(Set<Integer> operatingDays) { this.operatingDays = operatingDays; }
}
