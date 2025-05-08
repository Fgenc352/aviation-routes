package com.aviation.routes.controller;

import com.aviation.routes.dto.RouteDTO;
import com.aviation.routes.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService service;
    private final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;

    @Autowired
    public RouteController(RouteService service) {
        this.service = service;
    }

    @GetMapping
    public List<RouteDTO> search(
            @RequestParam(value = "origin",      required = false) String origin,
            @RequestParam(value = "destination", required = false) String destination,
            @RequestParam(value = "date",        required = false) String date
    ) {
        LocalDate d = (date == null || date.isBlank())
                ? null
                : LocalDate.parse(date, fmt);
        return service.findAllValidRoutes(origin, destination, d);
    }
}
