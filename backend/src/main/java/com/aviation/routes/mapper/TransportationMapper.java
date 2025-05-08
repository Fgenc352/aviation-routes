package com.aviation.routes.mapper;

import com.aviation.routes.dto.TransportationDTO;
import com.aviation.routes.entity.Location;
import com.aviation.routes.entity.Transportation;
import org.springframework.stereotype.Component;

@Component
public class TransportationMapper {

    public TransportationDTO toDto(Transportation e) {
        TransportationDTO dto = new TransportationDTO();
        dto.setId(e.getId());
        dto.setOriginCode(e.getOriginLocation().getLocationCode());
        dto.setDestinationCode(e.getDestinationLocation().getLocationCode());
        dto.setTransportationType(e.getTransportationType());
        dto.setOperatingDays(e.getOperatingDays());
        return dto;
    }

    public Transportation toEntity(
            TransportationDTO dto,
            Location origin,
            Location destination
    ) {
        Transportation e = new Transportation();
        e.setOriginLocation(origin);
        e.setDestinationLocation(destination);
        e.setTransportationType(dto.getTransportationType());
        e.setOperatingDays(dto.getOperatingDays());
        return e;
    }

    public void updateEntity(
            TransportationDTO dto,
            Transportation e,
            Location origin,
            Location destination
    ) {
        e.setOriginLocation(origin);
        e.setDestinationLocation(destination);
        e.setTransportationType(dto.getTransportationType());
        e.setOperatingDays(dto.getOperatingDays());
    }
}
