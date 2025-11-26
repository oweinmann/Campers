package com.caravan.controller;

import com.caravan.model.Caravan;
import com.caravan.service.CaravanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * REST Controller for Caravan operations
 */
@RestController
@RequestMapping("/caravans")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CaravanController {

    private final CaravanService caravanService;

    @GetMapping
    public ResponseEntity<List<Caravan>> getAllCaravans() {
        log.info("GET /caravans - Fetching all caravans");
        return ResponseEntity.ok(caravanService.getAllCaravans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caravan> getCaravanById(@PathVariable Long id) {
        log.info("GET /caravans/{} - Fetching caravan by id", id);
        return caravanService.getCaravanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Caravan>> getCaravansByPriority(@PathVariable Caravan.Priority priority) {
        log.info("GET /caravans/priority/{} - Fetching caravans by priority", priority);
        return ResponseEntity.ok(caravanService.getCaravansByPriority(priority));
    }

    @GetMapping("/origin/{origin}")
    public ResponseEntity<List<Caravan>> getCaravansByOrigin(@PathVariable String origin) {
        log.info("GET /caravans/origin/{} - Fetching caravans by origin", origin);
        return ResponseEntity.ok(caravanService.getCaravansByOrigin(origin));
    }

    @GetMapping("/make/{make}")
    public ResponseEntity<List<Caravan>> getCaravansByMake(@PathVariable String make) {
        log.info("GET /caravans/make/{} - Fetching caravans by make", make);
        return ResponseEntity.ok(caravanService.getCaravansByMake(make));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Caravan>> getCaravansByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        log.info("GET /caravans/price-range?minPrice={}&maxPrice={}", minPrice, maxPrice);
        return ResponseEntity.ok(caravanService.getCaravansByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/bunk-beds/{minBeds}")
    public ResponseEntity<List<Caravan>> getCaravansByMinimumBunkBeds(@PathVariable Integer minBeds) {
        log.info("GET /caravans/bunk-beds/{} - Fetching caravans with minimum bunk beds", minBeds);
        return ResponseEntity.ok(caravanService.getCaravansByMinimumBunkBeds(minBeds));
    }

    @PostMapping
    public ResponseEntity<Caravan> createCaravan(@Valid @RequestBody Caravan caravan) {
        log.info("POST /caravans - Creating new caravan");
        Caravan createdCaravan = caravanService.createCaravan(caravan);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCaravan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Caravan> updateCaravan(
            @PathVariable Long id,
            @Valid @RequestBody Caravan caravan) {
        log.info("PUT /caravans/{} - Updating caravan", id);
        try {
            Caravan updatedCaravan = caravanService.updateCaravan(id, caravan);
            return ResponseEntity.ok(updatedCaravan);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCaravan(@PathVariable Long id) {
        log.info("DELETE /caravans/{} - Deleting caravan", id);
        try {
            caravanService.deleteCaravan(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Void> restoreCaravan(@PathVariable Long id) {
        log.info("PUT /caravans/{}/restore - Restoring caravan", id);
        try {
            caravanService.restoreCaravan(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
