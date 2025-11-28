package com.caravan.repository;

import com.caravan.model.ExcludedCaravan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExcludedCaravanRepository extends JpaRepository<ExcludedCaravan, Long> {

    Optional<ExcludedCaravan> findByMakeAndModel(String make, String model);

    List<ExcludedCaravan> findByMake(String make);

    boolean existsByMakeAndModel(String make, String model);
}
