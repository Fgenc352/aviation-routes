package com.aviation.routes.controller;

import com.aviation.routes.dto.TransportationDTO;
import com.aviation.routes.service.TransportationService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transportations")
@Validated
public class TransportationController {

    private final TransportationService service;
    public TransportationController(TransportationService service) {
        this.service = service;
    }

    @GetMapping
    public List<TransportationDTO> listAll() {
        return service.listAll();
    }

    @PostMapping
    public TransportationDTO create(@Valid @RequestBody TransportationDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public TransportationDTO update(
            @PathVariable("id") Long id,
            @Valid @RequestBody TransportationDTO dto
    ) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
