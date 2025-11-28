package com.caravan.repository;

import com.caravan.model.ExcludedManufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExcludedManufacturerRepository extends JpaRepository<ExcludedManufacturer, Long> {

    Optional<ExcludedManufacturer> findByBrand(String brand);

    boolean existsByBrand(String brand);
}
