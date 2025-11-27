package com.caravan.controller;

import com.caravan.model.SearchResult;
import com.caravan.model.SearchResultDetail;
import com.caravan.service.SearchResultService;
import com.caravan.service.WebSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search-results")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class SearchResultController {

    private final SearchResultService searchResultService;
    private final WebSearchService webSearchService;

    @GetMapping
    public ResponseEntity<List<SearchResult>> getAllSearchResults() {
        return ResponseEntity.ok(searchResultService.getAllSearchResults());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SearchResult> getSearchResultById(@PathVariable Long id) {
        return searchResultService.getSearchResultById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<List<SearchResultDetail>> getSearchResultDetails(@PathVariable Long id) {
        return ResponseEntity.ok(searchResultService.getSearchResultDetails(id));
    }

    @GetMapping("/make/{make}/model/{model}")
    public ResponseEntity<List<SearchResult>> getSearchResultsByMakeAndModel(
            @PathVariable String make,
            @PathVariable String model) {
        return ResponseEntity.ok(searchResultService.getSearchResultsByMakeAndModel(make, model));
    }

    @GetMapping("/make/{make}")
    public ResponseEntity<List<SearchResult>> getSearchResultsByMake(@PathVariable String make) {
        return ResponseEntity.ok(searchResultService.getSearchResultsByMake(make));
    }

    @PostMapping
    public ResponseEntity<SearchResult> createSearchResult(@Valid @RequestBody SearchResult searchResult) {
        log.info("Received request to create search result for: {} {}",
                searchResult.getMake(), searchResult.getModel());
        SearchResult createdSearchResult = searchResultService.createSearchResult(searchResult);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSearchResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SearchResult> updateSearchResult(
            @PathVariable Long id,
            @Valid @RequestBody SearchResult searchResult) {
        try {
            SearchResult updatedSearchResult = searchResultService.updateSearchResult(id, searchResult);
            return ResponseEntity.ok(updatedSearchResult);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSearchResult(@PathVariable Long id) {
        try {
            searchResultService.deleteSearchResult(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/details")
    public ResponseEntity<SearchResultDetail> addSearchResultDetail(
            @PathVariable Long id,
            @Valid @RequestBody SearchResultDetail detail) {
        try {
            SearchResultDetail createdDetail = searchResultService.addSearchResultDetail(id, detail);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDetail);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/details/{detailId}")
    public ResponseEntity<Void> deleteSearchResultDetail(@PathVariable Long detailId) {
        try {
            searchResultService.deleteSearchResultDetail(detailId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/automated-search")
    public ResponseEntity<SearchResult> performAutomatedSearch(
            @RequestParam String make,
            @RequestParam String model) {
        log.info("Starting automated search for: {} {}", make, model);

        try {
            // Perform the automated search across websites
            SearchResult searchResult = webSearchService.performAutomatedSearch(make, model);

            // Save the search result to database
            SearchResult savedResult = searchResultService.createSearchResult(searchResult);

            log.info("Automated search completed and saved. Found {} results", savedResult.getTotalResults());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedResult);
        } catch (Exception e) {
            log.error("Error performing automated search: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
