package com.aviation.routes.mapper;

import com.aviation.routes.dto.LocationDTO;
import com.aviation.routes.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public LocationDTO toDto(Location entity) {
        LocationDTO dto = new LocationDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCountry(entity.getCountry());
        dto.setCity(entity.getCity());
        dto.setLocationCode(entity.getLocationCode());
        return dto;
    }

    public Location toEntity(LocationDTO dto) {
        Location entity = new Location();
        entity.setName(dto.getName());
        entity.setCountry(dto.getCountry());
        entity.setCity(dto.getCity());
        entity.setLocationCode(dto.getLocationCode());
        return entity;
    }

    public void updateEntity(LocationDTO dto, Location entity) {
        entity.setName(dto.getName());
        entity.setCountry(dto.getCountry());
        entity.setCity(dto.getCity());
        entity.setLocationCode(dto.getLocationCode());
    }
}
