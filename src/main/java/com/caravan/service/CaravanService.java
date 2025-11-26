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

@Service
@RequiredArgsConstructor
@Slf4j
public class CaravanService {

    private final CaravanRepository caravanRepository;

    public List<Caravan> getAllCaravans() {
        return caravanRepository.findByDeletedFalse();
    }

    public Optional<Caravan> getCaravanById(Long id) {
        return caravanRepository.findById(id).filter(caravan -> !caravan.getDeleted());
    }

    @Transactional
    public Caravan createCaravan(Caravan caravan) {
        caravan.setDeleted(false);
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
        caravan.setDeleted(true);
        log.info("Soft deleting caravan: {} {}", caravan.getMake(), caravan.getModel());
        caravanRepository.save(caravan);
    }

    public List<Caravan> getCaravansByPriority(Caravan.Priority priority) {
        return caravanRepository.findByDeletedFalseAndPriority(priority);
    }

    public List<Caravan> getCaravansByOrigin(String origin) {
        return caravanRepository.findByDeletedFalseAndOrigin(origin);
    }

    public List<Caravan> getCaravansByMake(String make) {
        return caravanRepository.findByDeletedFalseAndMake(make);
    }

    public List<Caravan> getCaravansByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return caravanRepository.findByDeletedFalseAndPriceBetween(minPrice, maxPrice);
    }

    public List<Caravan> getCaravansByMinimumBunkBeds(Integer minBunkBeds) {
        return caravanRepository.findByMinimumBunkBeds(minBunkBeds);
    }
}
