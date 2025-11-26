package com.caravan.repository;

import com.caravan.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Manufacturer entity
 */
@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    Optional<Manufacturer> findByBrand(String brand);

    List<Manufacturer> findByOrigin(String origin);

    List<Manufacturer> findByBrandContainingIgnoreCase(String brand);
}
