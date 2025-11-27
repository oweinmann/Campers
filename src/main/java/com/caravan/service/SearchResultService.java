package com.caravan.service;

import com.caravan.model.SearchResult;
import com.caravan.model.SearchResultDetail;
import com.caravan.repository.SearchResultRepository;
import com.caravan.repository.SearchResultDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchResultService {

    private final SearchResultRepository searchResultRepository;
    private final SearchResultDetailRepository searchResultDetailRepository;

    public List<SearchResult> getAllSearchResults() {
        return searchResultRepository.findAllByOrderBySearchDateDesc();
    }

    public Optional<SearchResult> getSearchResultById(Long id) {
        return searchResultRepository.findById(id);
    }

    public List<SearchResult> getSearchResultsByMakeAndModel(String make, String model) {
        return searchResultRepository.findByMakeAndModelOrderBySearchDateDesc(make, model);
    }

    public List<SearchResult> getSearchResultsByMake(String make) {
        return searchResultRepository.findByMakeOrderBySearchDateDesc(make);
    }

    public List<SearchResultDetail> getSearchResultDetails(Long searchResultId) {
        return searchResultDetailRepository.findBySearchResultId(searchResultId);
    }

    @Transactional
    public SearchResult createSearchResult(SearchResult searchResult) {
        log.info("Creating new search result for: {} {}", searchResult.getMake(), searchResult.getModel());

        // Save the search result first
        SearchResult savedResult = searchResultRepository.save(searchResult);

        // Set the search result reference for all details
        if (searchResult.getDetails() != null && !searchResult.getDetails().isEmpty()) {
            for (SearchResultDetail detail : searchResult.getDetails()) {
                detail.setSearchResult(savedResult);
            }
            savedResult.setTotalResults(searchResult.getDetails().size());
        } else {
            savedResult.setTotalResults(0);
        }

        return searchResultRepository.save(savedResult);
    }

    @Transactional
    public SearchResult updateSearchResult(Long id, SearchResult searchResultDetails) {
        SearchResult searchResult = searchResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Search result not found with id: " + id));

        searchResult.setMake(searchResultDetails.getMake());
        searchResult.setModel(searchResultDetails.getModel());
        searchResult.setSearchQuery(searchResultDetails.getSearchQuery());
        searchResult.setNotes(searchResultDetails.getNotes());
        searchResult.setTotalResults(searchResultDetails.getTotalResults());

        log.info("Updating search result: {}", id);
        return searchResultRepository.save(searchResult);
    }

    @Transactional
    public void deleteSearchResult(Long id) {
        log.info("Deleting search result: {}", id);
        searchResultRepository.deleteById(id);
    }

    @Transactional
    public SearchResultDetail addSearchResultDetail(Long searchResultId, SearchResultDetail detail) {
        SearchResult searchResult = searchResultRepository.findById(searchResultId)
                .orElseThrow(() -> new RuntimeException("Search result not found with id: " + searchResultId));

        detail.setSearchResult(searchResult);
        SearchResultDetail savedDetail = searchResultDetailRepository.save(detail);

        // Update total results count
        searchResult.setTotalResults(searchResult.getDetails().size());
        searchResultRepository.save(searchResult);

        return savedDetail;
    }

    @Transactional
    public void deleteSearchResultDetail(Long detailId) {
        log.info("Deleting search result detail: {}", detailId);
        searchResultDetailRepository.deleteById(detailId);
    }
}
