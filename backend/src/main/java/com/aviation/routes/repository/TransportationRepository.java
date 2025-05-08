package com.aviation.routes.repository;

import com.aviation.routes.entity.Transportation;
import com.aviation.routes.entity.TransportationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportationRepository extends JpaRepository<Transportation, Long> {
    List<Transportation> findByOriginLocationId(Long originId);
    List<Transportation> findByDestinationLocationId(Long destinationId);

    boolean existsByOriginLocation_LocationCodeAndDestinationLocation_LocationCodeAndTransportationType(
            String originCode,
            String destinationCode,
            TransportationType transportationType
    );

    boolean existsByOriginLocation_LocationCodeAndDestinationLocation_LocationCodeAndTransportationTypeAndIdNot(
            String originCode,
            String destinationCode,
            TransportationType transportationType,
            Long id
    );
}
