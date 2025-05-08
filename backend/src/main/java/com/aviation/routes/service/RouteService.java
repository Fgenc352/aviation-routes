package com.aviation.routes.service;

import com.aviation.routes.dto.RouteDTO;
import java.time.LocalDate;
import java.util.List;

public interface RouteService {
    List<RouteDTO> findAllValidRoutes(
            String originCode,
            String destinationCode,
            LocalDate date
    );
}
