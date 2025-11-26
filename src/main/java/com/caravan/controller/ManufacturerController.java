package com.caravan.controller;

import com.caravan.model.Manufacturer;
import com.caravan.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller for Manufacturer operations
 */
@RestController
@RequestMapping("/manufacturers")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @GetMapping
    public ResponseEntity<List<Manufacturer>> getAllManufacturers() {
        log.info("GET /manufacturers - Fetching all manufacturers");
        return ResponseEntity.ok(manufacturerService.getAllManufacturers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable Long id) {
        log.info("GET /manufacturers/{} - Fetching manufacturer by id", id);
        return manufacturerService.getManufacturerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<Manufacturer> getManufacturerByBrand(@PathVariable String brand) {
        log.info("GET /manufacturers/brand/{} - Fetching manufacturer by brand", brand);
        return manufacturerService.getManufacturerByBrand(brand)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/origin/{origin}")
    public ResponseEntity<List<Manufacturer>> getManufacturersByOrigin(@PathVariable String origin) {
        log.info("GET /manufacturers/origin/{} - Fetching manufacturers by origin", origin);
        return ResponseEntity.ok(manufacturerService.getManufacturersByOrigin(origin));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Manufacturer>> searchManufacturers(@RequestParam String term) {
        log.info("GET /manufacturers/search?term={}", term);
        return ResponseEntity.ok(manufacturerService.searchManufacturers(term));
    }

    @PostMapping
    public ResponseEntity<Manufacturer> createManufacturer(@Valid @RequestBody Manufacturer manufacturer) {
        log.info("POST /manufacturers - Creating new manufacturer");
        Manufacturer createdManufacturer = manufacturerService.createManufacturer(manufacturer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdManufacturer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manufacturer> updateManufacturer(
            @PathVariable Long id,
            @Valid @RequestBody Manufacturer manufacturer) {
        log.info("PUT /manufacturers/{} - Updating manufacturer", id);
        try {
            Manufacturer updatedManufacturer = manufacturerService.updateManufacturer(id, manufacturer);
            return ResponseEntity.ok(updatedManufacturer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long id) {
        log.info("DELETE /manufacturers/{} - Deleting manufacturer", id);
        try {
            manufacturerService.deleteManufacturer(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
