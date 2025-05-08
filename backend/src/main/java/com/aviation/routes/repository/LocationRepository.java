package com.aviation.routes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aviation.routes.entity.Location;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByLocationCode(String locationCode);
    boolean existsByLocationCode(String locationCode);
}
