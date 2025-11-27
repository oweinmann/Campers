package com.caravan.repository;

import com.caravan.model.SearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchResultRepository extends JpaRepository<SearchResult, Long> {

    List<SearchResult> findByMakeAndModelOrderBySearchDateDesc(String make, String model);

    List<SearchResult> findAllByOrderBySearchDateDesc();

    List<SearchResult> findByMakeOrderBySearchDateDesc(String make);
}
