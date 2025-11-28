package com.caravan.service;

import com.caravan.model.Caravan;
import com.caravan.model.ExcludedCaravan;
import com.caravan.repository.CaravanRepository;
import com.caravan.repository.ExcludedCaravanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaravanService {

    private final CaravanRepository caravanRepository;
    private final ExcludedCaravanRepository excludedCaravanRepository;

    public List<Caravan> getAllCaravans() {
        return caravanRepository.findAll();
    }

    public Optional<Caravan> getCaravanById(Long id) {
        return caravanRepository.findById(id);
    }

    @Transactional
    public Caravan createCaravan(Caravan caravan) {
        log.info("Creating new caravan: {} {}", caravan.getMake(), caravan.getModel());
        return caravanRepository.save(caravan);
    }

    @Transactional
    public Caravan updateCaravan(Long id, Caravan caravanDetails) {
        Caravan caravan = caravanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caravan not found with id: " + id));
        caravan.setMake(caravanDetails.getMake());
        caravan.setModel(caravanDetails.getModel());
        caravan.setOrigin(caravanDetails.getOrigin());
        caravan.setPrice(caravanDetails.getPrice());
        caravan.setWebsite(caravanDetails.getWebsite());
        caravan.setExternalLength(caravanDetails.getExternalLength());
        caravan.setExternalWidth(caravanDetails.getExternalWidth());
        caravan.setExternalHeight(caravanDetails.getExternalHeight());
        caravan.setInternalHeight(caravanDetails.getInternalHeight());
        caravan.setTareWeight(caravanDetails.getTareWeight());
        caravan.setAtm(caravanDetails.getAtm());
        caravan.setBallWeight(caravanDetails.getBallWeight());
        caravan.setGtm(caravanDetails.getGtm());
        caravan.setMainBed(caravanDetails.getMainBed());
        caravan.setBunkBeds(caravanDetails.getBunkBeds());
        caravan.setBunkType(caravanDetails.getBunkType());
        caravan.setPriority(caravanDetails.getPriority());
        caravan.setStatus(caravanDetails.getStatus());
        caravan.setFeatures(caravanDetails.getFeatures());
        caravan.setNotes(caravanDetails.getNotes());
        log.info("Updating caravan: {} {}", caravan.getMake(), caravan.getModel());
        return caravanRepository.save(caravan);
    }

    @Transactional
    public void deleteCaravan(Long id) {
        Caravan caravan = caravanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caravan not found with id: " + id));

        // Create excluded entry
        ExcludedCaravan excludedCaravan = new ExcludedCaravan();
        excludedCaravan.setMake(caravan.getMake());
        excludedCaravan.setModel(caravan.getModel());
        excludedCaravanRepository.save(excludedCaravan);

        // Delete from caravans table
        caravanRepository.delete(caravan);

        log.info("Excluded caravan: {} {} and removed from active list", caravan.getMake(), caravan.getModel());
    }

    public List<Caravan> getCaravansByPriority(Caravan.Priority priority) {
        return caravanRepository.findByPriority(priority);
    }

    public List<Caravan> getCaravansByOrigin(String origin) {
        return caravanRepository.findByOrigin(origin);
    }

    public List<Caravan> getCaravansByMake(String make) {
        return caravanRepository.findByMake(make);
    }

    public List<Caravan> getCaravansByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return caravanRepository.findByPriceBetween(minPrice, maxPrice);
    }

    // Methods for excluded caravans
    public List<ExcludedCaravan> getAllExcludedCaravans() {
        return excludedCaravanRepository.findAll();
    }

    public boolean isCaravanExcluded(String make, String model) {
        return excludedCaravanRepository.existsByMakeAndModel(make, model);
    }

    public List<Caravan> getCaravansByMinimumBunkBeds(Integer minBunkBeds) {
        return caravanRepository.findByMinimumBunkBeds(minBunkBeds);
    }
}
