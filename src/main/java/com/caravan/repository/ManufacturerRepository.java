package com.caravan.repository;

import com.caravan.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    List<Manufacturer> findByOrigin(String origin);

    List<Manufacturer> findByBrandContainingIgnoreCase(String brand);

    Optional<Manufacturer> findByBrandIgnoreCase(String brand);
}
