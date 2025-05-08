package com.aviation.routes.service.impl;

import com.aviation.routes.dto.LocationDTO;
import com.aviation.routes.entity.Location;
import com.aviation.routes.exception.BadRequestException;
import com.aviation.routes.exception.ResourceNotFoundException;
import com.aviation.routes.mapper.LocationMapper;
import com.aviation.routes.repository.LocationRepository;
import com.aviation.routes.repository.TransportationRepository;
import com.aviation.routes.service.LocationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    private final LocationRepository       locationRepo;
    private final TransportationRepository transportationRepo;
    private final LocationMapper           mapper;

    public LocationServiceImpl(
            LocationRepository locationRepo,
            TransportationRepository transportationRepo,
            LocationMapper mapper
    ) {
        this.locationRepo       = locationRepo;
        this.transportationRepo = transportationRepo;
        this.mapper             = mapper;
    }

    @Override
    public List<LocationDTO> listAll() {
        return locationRepo.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LocationDTO create(LocationDTO dto) {

        if (locationRepo.existsByLocationCode(dto.getLocationCode())) {
            throw new BadRequestException(
                    "Location code '" + dto.getLocationCode() + "' already exists"
            );
        }
        Location saved = locationRepo.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Override
    public LocationDTO update(Long id, LocationDTO dto) {
        Location existing = locationRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Location not found: " + id)
                );

        if (!existing.getLocationCode().equalsIgnoreCase(dto.getLocationCode())
                && locationRepo.existsByLocationCode(dto.getLocationCode())) {
            throw new BadRequestException(
                    "Location code '" + dto.getLocationCode() + "' already exists"
            );
        }

        mapper.updateEntity(dto, existing);
        return mapper.toDto(locationRepo.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!locationRepo.existsById(id)) {
            throw new ResourceNotFoundException("Location not found: " + id);
        }

        transportationRepo.deleteAll(
                transportationRepo.findByOriginLocationId(id)
        );
        transportationRepo.deleteAll(
                transportationRepo.findByDestinationLocationId(id)
        );

        locationRepo.deleteById(id);
    }
}

