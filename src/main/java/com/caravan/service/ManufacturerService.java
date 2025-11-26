package com.caravan.service;

import com.caravan.model.Manufacturer;
import com.caravan.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Optional<Manufacturer> getManufacturerById(Long id) {
        return manufacturerRepository.findById(id);
    }

    @Transactional
    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        log.info("Creating new manufacturer: {}", manufacturer.getBrand());
        return manufacturerRepository.save(manufacturer);
    }

    @Transactional
    public Manufacturer updateManufacturer(Long id, Manufacturer manufacturerDetails) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found with id: " + id));
        manufacturer.setBrand(manufacturerDetails.getBrand());
        manufacturer.setOrigin(manufacturerDetails.getOrigin());
        manufacturer.setWebsite(manufacturerDetails.getWebsite());
        manufacturer.setKnownFor(manufacturerDetails.getKnownFor());
        manufacturer.setKeyModels(manufacturerDetails.getKeyModels());
        manufacturer.setNotes(manufacturerDetails.getNotes());
        log.info("Updating manufacturer: {}", manufacturer.getBrand());
        return manufacturerRepository.save(manufacturer);
    }

    @Transactional
    public void deleteManufacturer(Long id) {
        log.info("Deleting manufacturer with id: {}", id);
        manufacturerRepository.deleteById(id);
    }

    public List<Manufacturer> getManufacturersByOrigin(String origin) {
        return manufacturerRepository.findByOrigin(origin);
    }

    public List<Manufacturer> searchManufacturers(String query) {
        return manufacturerRepository.findByBrandContainingIgnoreCase(query);
    }
}
