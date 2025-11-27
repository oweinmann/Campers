package com.caravan.repository;

import com.caravan.model.SearchResultDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchResultDetailRepository extends JpaRepository<SearchResultDetail, Long> {

    List<SearchResultDetail> findBySearchResultId(Long searchResultId);
}
