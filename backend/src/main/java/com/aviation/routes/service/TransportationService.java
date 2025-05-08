package com.aviation.routes.service;

import com.aviation.routes.dto.TransportationDTO;
import java.util.List;

public interface TransportationService {
    List<TransportationDTO> listAll();
    TransportationDTO create(TransportationDTO dto);
    TransportationDTO update(Long id, TransportationDTO dto);
    void delete(Long id);
}
