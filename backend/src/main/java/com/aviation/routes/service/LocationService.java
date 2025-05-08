package com.aviation.routes.service;

import com.aviation.routes.dto.LocationDTO;
import java.util.List;

public interface LocationService {
    List<LocationDTO> listAll();
    LocationDTO create(LocationDTO dto);
    LocationDTO update(Long id, LocationDTO dto);
    void delete(Long id);
}
