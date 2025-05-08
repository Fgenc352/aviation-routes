package com.aviation.routes.controller;

import com.aviation.routes.dto.LocationDTO;
import com.aviation.routes.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@Validated
public class LocationController {

    private final LocationService service;
    public LocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping
    public List<LocationDTO> listAll() {
        return service.listAll();
    }

    @PostMapping
    public LocationDTO create(@Valid @RequestBody LocationDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public LocationDTO update(
            @PathVariable("id") Long id,
            @Valid @RequestBody LocationDTO dto
    ) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
