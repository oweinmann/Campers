package com.caravan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "search_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(name = "search_date", nullable = false)
    private LocalDateTime searchDate;

    @Column(name = "total_results")
    private Integer totalResults;

    @Column(columnDefinition = "TEXT")
    private String searchQuery;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "searchResult", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SearchResultDetail> details = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (searchDate == null) {
            searchDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
