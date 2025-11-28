package com.caravan.service;

import com.caravan.model.Caravan;
import com.caravan.model.ExcludedCaravan;
import com.caravan.model.ExcludedManufacturer;
import com.caravan.model.Manufacturer;
import com.caravan.repository.CaravanRepository;
import com.caravan.repository.ExcludedCaravanRepository;
import com.caravan.repository.ExcludedManufacturerRepository;
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
    private final CaravanRepository caravanRepository;
    private final ExcludedCaravanRepository excludedCaravanRepository;
    private final ExcludedManufacturerRepository excludedManufacturerRepository;

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
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found with id: " + id));

        String brand = manufacturer.getBrand();

        // Find all caravans from this manufacturer
        List<Caravan> caravansToExclude = caravanRepository.findByMake(brand);

        // Move all caravans to excludedCaravans table
        for (Caravan caravan : caravansToExclude) {
            ExcludedCaravan excludedCaravan = new ExcludedCaravan();
            excludedCaravan.setMake(caravan.getMake());
            excludedCaravan.setModel(caravan.getModel());
            excludedCaravanRepository.save(excludedCaravan);
            log.info("Excluded caravan: {} {} due to manufacturer exclusion", caravan.getMake(), caravan.getModel());
        }

        // Delete all caravans from this manufacturer
        caravanRepository.deleteAll(caravansToExclude);

        // Add manufacturer to excluded list
        ExcludedManufacturer excludedManufacturer = new ExcludedManufacturer();
        excludedManufacturer.setBrand(brand);
        excludedManufacturerRepository.save(excludedManufacturer);

        // Delete manufacturer from manufacturers table
        manufacturerRepository.delete(manufacturer);

        log.info("Excluded manufacturer: {} and all {} associated caravans", brand, caravansToExclude.size());
    }

    public List<Manufacturer> getManufacturersByOrigin(String origin) {
        return manufacturerRepository.findByOrigin(origin);
    }

    public List<Manufacturer> searchManufacturers(String query) {
        return manufacturerRepository.findByBrandContainingIgnoreCase(query);
    }

    // Methods for excluded manufacturers
    public List<ExcludedManufacturer> getAllExcludedManufacturers() {
        return excludedManufacturerRepository.findAll();
    }

    public boolean isManufacturerExcluded(String brand) {
        return excludedManufacturerRepository.existsByBrand(brand);
    }
}
