package com.caravan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "excludedManufacturers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcludedManufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Brand is required")
    @Column(nullable = false, unique = true)
    private String brand;

    @Column(name = "date_excluded", nullable = false, updatable = false)
    private LocalDateTime dateExcluded;

    @PrePersist
    protected void onCreate() {
        dateExcluded = LocalDateTime.now();
    }
}
