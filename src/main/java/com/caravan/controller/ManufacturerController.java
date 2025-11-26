package com.caravan.controller;

import com.caravan.model.Manufacturer;
import com.caravan.service.ManufacturerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manufacturers")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @GetMapping
    public ResponseEntity<List<Manufacturer>> getAllManufacturers() {
        return ResponseEntity.ok(manufacturerService.getAllManufacturers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable Long id) {
        return manufacturerService.getManufacturerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Manufacturer> createManufacturer(@Valid @RequestBody Manufacturer manufacturer) {
        Manufacturer createdManufacturer = manufacturerService.createManufacturer(manufacturer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdManufacturer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manufacturer> updateManufacturer(@PathVariable Long id, @Valid @RequestBody Manufacturer manufacturer) {
        try {
            Manufacturer updatedManufacturer = manufacturerService.updateManufacturer(id, manufacturer);
            return ResponseEntity.ok(updatedManufacturer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long id) {
        try {
            manufacturerService.deleteManufacturer(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/origin/{origin}")
    public ResponseEntity<List<Manufacturer>> getManufacturersByOrigin(@PathVariable String origin) {
        return ResponseEntity.ok(manufacturerService.getManufacturersByOrigin(origin));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Manufacturer>> searchManufacturers(@RequestParam String query) {
        return ResponseEntity.ok(manufacturerService.searchManufacturers(query));
    }
}
