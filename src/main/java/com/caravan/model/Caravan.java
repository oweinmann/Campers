package com.caravan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "caravans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caravan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Make is required")
    @Column(nullable = false)
    private String make;

    @NotBlank(message = "Model is required")
    @Column(nullable = false)
    private String model;

    @NotBlank(message = "Origin is required")
    @Column(nullable = false)
    private String origin; // Australian Made or Imported

    @NotNull(message = "Price is required")
    @Column(nullable = false)
    private BigDecimal price;

    private String website;

    // Dimensions
    @Column(name = "external_length")
    private String externalLength;

    @Column(name = "external_width")
    private String externalWidth;

    @Column(name = "external_height")
    private String externalHeight;

    @Column(name = "internal_height")
    private String internalHeight;

    // Weight specifications
    @Column(name = "tare_weight")
    private Integer tareWeight;

    private Integer atm; // Aggregate Trailer Mass

    @Column(name = "ball_weight")
    private Integer ballWeight;

    private Integer gtm; // Gross Trailer Mass

    // Sleeping arrangements
    @Column(name = "main_bed")
    private String mainBed;

    @Column(name = "bunk_beds")
    private Integer bunkBeds;

    @Column(name = "bunk_type")
    private String bunkType;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(columnDefinition = "TEXT")
    private String features;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (deleted == null) {
            deleted = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum Priority {
        HIGH, MEDIUM, BUDGET, PREMIUM
    }

    public enum Status {
        RESEARCHING, SHORTLISTED, EXCLUDED, PURCHASED
    }
}
