package com.caravan.service;

import com.caravan.model.Manufacturer;
import com.caravan.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for Manufacturer business logic
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> getAllManufacturers() {
        log.debug("Fetching all manufacturers");
        return manufacturerRepository.findAll();
    }

    public Optional<Manufacturer> getManufacturerById(Long id) {
        log.debug("Fetching manufacturer with id: {}", id);
        return manufacturerRepository.findById(id);
    }

    public Optional<Manufacturer> getManufacturerByBrand(String brand) {
        log.debug("Fetching manufacturer with brand: {}", brand);
        return manufacturerRepository.findByBrand(brand);
    }

    public List<Manufacturer> getManufacturersByOrigin(String origin) {
        log.debug("Fetching manufacturers with origin: {}", origin);
        return manufacturerRepository.findByOrigin(origin);
    }

    public List<Manufacturer> searchManufacturers(String searchTerm) {
        log.debug("Searching manufacturers with term: {}", searchTerm);
        return manufacturerRepository.findByBrandContainingIgnoreCase(searchTerm);
    }

    @Transactional
    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        log.info("Creating new manufacturer: {}", manufacturer.getBrand());
        return manufacturerRepository.save(manufacturer);
    }

    @Transactional
    public Manufacturer updateManufacturer(Long id, Manufacturer manufacturerDetails) {
        log.info("Updating manufacturer with id: {}", id);
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found with id: " + id));

        manufacturer.setBrand(manufacturerDetails.getBrand());
        manufacturer.setOrigin(manufacturerDetails.getOrigin());
        manufacturer.setWebsite(manufacturerDetails.getWebsite());
        manufacturer.setKnownFor(manufacturerDetails.getKnownFor());
        manufacturer.setKeyModels(manufacturerDetails.getKeyModels());
        manufacturer.setNotes(manufacturerDetails.getNotes());

        return manufacturerRepository.save(manufacturer);
    }

    @Transactional
    public void deleteManufacturer(Long id) {
        log.info("Deleting manufacturer with id: {}", id);
        manufacturerRepository.deleteById(id);
    }
}
