package com.caravan.service;

import com.caravan.model.Caravan;
import com.caravan.repository.CaravanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service for Caravan business logic
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CaravanService {

    private final CaravanRepository caravanRepository;

    public List<Caravan> getAllCaravans() {
        log.debug("Fetching all active caravans");
        return caravanRepository.findByDeletedFalse();
    }

    public Optional<Caravan> getCaravanById(Long id) {
        log.debug("Fetching caravan with id: {}", id);
        return caravanRepository.findById(id);
    }

    public List<Caravan> getCaravansByPriority(Caravan.Priority priority) {
        log.debug("Fetching caravans with priority: {}", priority);
        return caravanRepository.findByDeletedFalseAndPriority(priority);
    }

    public List<Caravan> getCaravansByOrigin(String origin) {
        log.debug("Fetching caravans with origin: {}", origin);
        return caravanRepository.findByDeletedFalseAndOrigin(origin);
    }

    public List<Caravan> getCaravansByMake(String make) {
        log.debug("Fetching caravans with make: {}", make);
        return caravanRepository.findByDeletedFalseAndMake(make);
    }

    public List<Caravan> getCaravansByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        log.debug("Fetching caravans in price range: {} - {}", minPrice, maxPrice);
        return caravanRepository.findByDeletedFalseAndPriceBetween(minPrice, maxPrice);
    }

    public List<Caravan> getCaravansByMinimumBunkBeds(Integer minBunkBeds) {
        log.debug("Fetching caravans with minimum {} bunk beds", minBunkBeds);
        return caravanRepository.findByMinimumBunkBeds(minBunkBeds);
    }

    @Transactional
    public Caravan createCaravan(Caravan caravan) {
        log.info("Creating new caravan: {} {}", caravan.getMake(), caravan.getModel());
        caravan.setDeleted(false);
        return caravanRepository.save(caravan);
    }

    @Transactional
    public Caravan updateCaravan(Long id, Caravan caravanDetails) {
        log.info("Updating caravan with id: {}", id);
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
        caravan.setAxleConfig(caravanDetails.getAxleConfig());
        caravan.setAxleCount(caravanDetails.getAxleCount());
        caravan.setSolarPanels(caravanDetails.getSolarPanels());
        caravan.setBatteryCapacity(caravanDetails.getBatteryCapacity());
        caravan.setFreshWaterCapacity(caravanDetails.getFreshWaterCapacity());
        caravan.setGreyWaterCapacity(caravanDetails.getGreyWaterCapacity());
        caravan.setMainBed(caravanDetails.getMainBed());
        caravan.setBunkBeds(caravanDetails.getBunkBeds());
        caravan.setBunkType(caravanDetails.getBunkType());
        caravan.setFeatures(caravanDetails.getFeatures());
        caravan.setNotes(caravanDetails.getNotes());
        caravan.setPriority(caravanDetails.getPriority());
        caravan.setStatus(caravanDetails.getStatus());

        return caravanRepository.save(caravan);
    }

    @Transactional
    public void deleteCaravan(Long id) {
        log.info("Soft deleting caravan with id: {}", id);
        Caravan caravan = caravanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caravan not found with id: " + id));
        caravan.setDeleted(true);
        caravanRepository.save(caravan);
    }

    @Transactional
    public void restoreCaravan(Long id) {
        log.info("Restoring caravan with id: {}", id);
        Caravan caravan = caravanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caravan not found with id: " + id));
        caravan.setDeleted(false);
        caravanRepository.save(caravan);
    }
}
