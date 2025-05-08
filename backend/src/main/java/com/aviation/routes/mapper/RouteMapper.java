package com.aviation.routes.mapper;

import com.aviation.routes.dto.RouteDTO;
import com.aviation.routes.dto.RouteSegmentDTO;
import com.aviation.routes.entity.Transportation;

import java.util.List;
import java.util.stream.Collectors;

public class RouteMapper {

    public static RouteDTO toDto(List<Transportation> segments) {
        List<RouteSegmentDTO> segDtos = segments.stream()
                .map(tx -> {
                    RouteSegmentDTO seg = new RouteSegmentDTO();
                    seg.setOriginCode(tx.getOriginLocation().getLocationCode());
                    seg.setDestinationCode(tx.getDestinationLocation().getLocationCode());
                    seg.setTransportationType(tx.getTransportationType());
                    return seg;
                })
                .collect(Collectors.toList());

        RouteDTO dto = new RouteDTO();
        dto.setSegments(segDtos);
        return dto;
    }
}
