package com.caravan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "search_result_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "search_result_id", nullable = false)
    @JsonIgnore
    private SearchResult searchResult;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal price;

    @Column(name = "listing_url", columnDefinition = "TEXT")
    private String listingUrl;

    private String location;

    private String seller;

    private Integer year;

    private String condition; // e.g., "Used - Excellent", "Used - Good"

    @Column(columnDefinition = "TEXT")
    private String specifications;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "posted_date")
    private LocalDateTime postedDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
