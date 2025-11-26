package com.caravan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a Caravan
 */
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

    @Column(length = 500)
    private String website;

    // Dimensions
    private String externalLength; // e.g., "4.88m / 16ft"
    private String externalWidth;
    private String externalHeight;
    private String internalHeight;

    // Weight specifications
    private Integer tareWeight; // kg
    private Integer atm; // Aggregate Trailer Mass (kg)
    private Integer ballWeight; // kg
    private Integer gtm; // Gross Trailer Mass (kg)

    // Configuration
    private String axleConfig; // Single or Twin
    private Integer axleCount;

    // Power & Water
    private String solarPanels; // e.g., "300W"
    private String batteryCapacity; // e.g., "200Ah Lithium"
    private Integer freshWaterCapacity; // Liters
    private Integer greyWaterCapacity; // Liters

    // Sleeping arrangements
    private String mainBed; // e.g., "Queen"
    private Integer bunkBeds;
    private String bunkType; // e.g., "2x Singles"

    // Features
    @Column(length = 1000)
    private String features;

    @Column(length = 2000)
    private String notes;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Boolean deleted = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
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
