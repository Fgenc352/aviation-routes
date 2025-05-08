package com.aviation.routes.service.impl;

import com.aviation.routes.dto.RouteDTO;
import com.aviation.routes.dto.RouteSegmentDTO;
import com.aviation.routes.entity.Transportation;
import com.aviation.routes.entity.TransportationType;
import com.aviation.routes.repository.TransportationRepository;
import com.aviation.routes.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    private final TransportationRepository txRepo;

    @Autowired
    public RouteServiceImpl(TransportationRepository txRepo) {
        this.txRepo = txRepo;
    }

    @Override
    public List<RouteDTO> findAllValidRoutes(
            String originCode,
            String destinationCode,
            LocalDate date
    ) {
        List<Transportation> all = txRepo.findAll();

        if (date != null) {
            int dow = date.getDayOfWeek().getValue();
            all = all.stream()
                    .filter(tx -> tx.getOperatingDays().contains(dow))
                    .collect(Collectors.toList());
        }

        Map<String, List<Transportation>> byOrigin = all.stream()
                .collect(Collectors.groupingBy(tx -> tx.getOriginLocation().getLocationCode()));

        List<List<Transportation>> rawRoutes = new ArrayList<>();
        boolean originSpecified = originCode != null && !originCode.isBlank();

        if (originSpecified) {
            dfs(originCode, destinationCode, byOrigin, new ArrayList<>(), rawRoutes);
        } else {
            for (String start : byOrigin.keySet()) {
                dfs(start, destinationCode, byOrigin, new ArrayList<>(), rawRoutes);
            }
        }

        return rawRoutes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private void dfs(
            String current,
            String destination,
            Map<String, List<Transportation>> byOrigin,
            List<Transportation> path,
            List<List<Transportation>> result
    ) {
        if (path.size() > 3) {
            return;
        }

        boolean hasFlight = path.stream()
                .anyMatch(tx -> tx.getTransportationType() == TransportationType.FLIGHT);
        boolean destSpecified = destination != null && !destination.isBlank();

        if (hasFlight && (!destSpecified || current.equals(destination))) {
            long flightCount = path.stream()
                    .filter(tx -> tx.getTransportationType() == TransportationType.FLIGHT)
                    .count();
            if (flightCount == 1) {
                result.add(new ArrayList<>(path));
            }
            if (destSpecified && current.equals(destination)) {
                return;
            }
        }

        for (Transportation next : byOrigin.getOrDefault(current, Collections.emptyList())) {
            if (path.contains(next)) {
                continue;
            }
            if (!validExtend(path, next)) {
                continue;
            }
            path.add(next);
            dfs(next.getDestinationLocation().getLocationCode(),
                    destination, byOrigin, path, result);
            path.remove(path.size() - 1);
        }
    }

    private boolean validExtend(List<Transportation> path, Transportation next) {
        long flightsSoFar = path.stream()
                .filter(tx -> tx.getTransportationType() == TransportationType.FLIGHT)
                .count();

        if (next.getTransportationType() == TransportationType.FLIGHT) {
            return flightsSoFar == 0;
        }

        if (flightsSoFar == 0) {
            long before = path.stream()
                    .filter(tx -> tx.getTransportationType() != TransportationType.FLIGHT)
                    .count();
            return before < 1;
        }

        int flightIndex = -1;
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i).getTransportationType() == TransportationType.FLIGHT) {
                flightIndex = i;
                break;
            }
        }
        long afterCount = path.subList(flightIndex + 1, path.size()).stream()
                .filter(tx -> tx.getTransportationType() != TransportationType.FLIGHT)
                .count();
        return afterCount < 1;
    }

    private RouteDTO toDto(List<Transportation> path) {
        List<RouteSegmentDTO> segs = path.stream()
                .map(tx -> {
                    RouteSegmentDTO s = new RouteSegmentDTO();
                    s.setOriginCode(tx.getOriginLocation().getLocationCode());
                    s.setDestinationCode(tx.getDestinationLocation().getLocationCode());
                    s.setTransportationType(tx.getTransportationType());
                    s.setOperatingDays(tx.getOperatingDays());
                    return s;
                })
                .collect(Collectors.toList());
        RouteDTO dto = new RouteDTO();
        dto.setSegments(segs);
        return dto;
    }
}
