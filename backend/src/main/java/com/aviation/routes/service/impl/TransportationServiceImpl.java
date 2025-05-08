package com.aviation.routes.service.impl;

import com.aviation.routes.dto.TransportationDTO;
import com.aviation.routes.entity.Location;
import com.aviation.routes.entity.Transportation;
import com.aviation.routes.exception.BadRequestException;
import com.aviation.routes.exception.ResourceNotFoundException;
import com.aviation.routes.mapper.TransportationMapper;
import com.aviation.routes.repository.LocationRepository;
import com.aviation.routes.repository.TransportationRepository;
import com.aviation.routes.service.TransportationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransportationServiceImpl implements TransportationService {

    private final TransportationRepository transportRepo;
    private final LocationRepository       locationRepo;
    private final TransportationMapper     mapper;

    public TransportationServiceImpl(
            TransportationRepository transportRepo,
            LocationRepository locationRepo,
            TransportationMapper mapper
    ) {
        this.transportRepo = transportRepo;
        this.locationRepo  = locationRepo;
        this.mapper        = mapper;
    }

    @Override
    public List<TransportationDTO> listAll() {
        return transportRepo.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransportationDTO create(TransportationDTO dto) {
        if (dto.getOriginCode().equals(dto.getDestinationCode())) {
            throw new BadRequestException("Origin and destination cannot be the same");
        }

        if (transportRepo.existsByOriginLocation_LocationCodeAndDestinationLocation_LocationCodeAndTransportationType(
                dto.getOriginCode(),
                dto.getDestinationCode(),
                dto.getTransportationType()
        )) {
            throw new BadRequestException(String.format(
                    "A %s from %s to %s already exists",
                    dto.getTransportationType(),
                    dto.getOriginCode(),
                    dto.getDestinationCode()
            ));
        }

        Location origin = locationRepo.findByLocationCode(dto.getOriginCode())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Origin not found: " + dto.getOriginCode())
                );
        Location dest = locationRepo.findByLocationCode(dto.getDestinationCode())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Destination not found: " + dto.getDestinationCode())
                );

        Transportation entity = mapper.toEntity(dto, origin, dest);
        return mapper.toDto(transportRepo.save(entity));
    }

    @Override
    public TransportationDTO update(Long id, TransportationDTO dto) {
        Transportation existing = transportRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Transportation not found: " + id)
                );

        if (dto.getOriginCode().equals(dto.getDestinationCode())) {
            throw new BadRequestException("Origin and destination cannot be the same");
        }

        if (transportRepo.existsByOriginLocation_LocationCodeAndDestinationLocation_LocationCodeAndTransportationTypeAndIdNot(
                dto.getOriginCode(),
                dto.getDestinationCode(),
                dto.getTransportationType(),
                id
        )) {
            throw new BadRequestException(String.format(
                    "A %s from %s to %s already exists",
                    dto.getTransportationType(),
                    dto.getOriginCode(),
                    dto.getDestinationCode()
            ));
        }

        Location origin = locationRepo.findByLocationCode(dto.getOriginCode())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Origin not found: " + dto.getOriginCode())
                );
        Location dest = locationRepo.findByLocationCode(dto.getDestinationCode())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Destination not found: " + dto.getDestinationCode())
                );

        mapper.updateEntity(dto, existing, origin, dest);
        return mapper.toDto(transportRepo.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!transportRepo.existsById(id)) {
            throw new ResourceNotFoundException("Transportation not found: " + id);
        }
        transportRepo.deleteById(id);
    }
}
