package com.caravan.repository;

import com.caravan.model.Caravan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CaravanRepository extends JpaRepository<Caravan, Long> {

    List<Caravan> findByPriority(Caravan.Priority priority);

    List<Caravan> findByOrigin(String origin);

    List<Caravan> findByMake(String make);

    List<Caravan> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    @Query("SELECT c FROM Caravan c WHERE c.bunkBeds >= :minBunkBeds")
    List<Caravan> findByMinimumBunkBeds(@Param("minBunkBeds") Integer minBunkBeds);
}
