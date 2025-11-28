package com.caravan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "excludedCaravans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcludedCaravan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Make is required")
    @Column(nullable = false)
    private String make;

    @NotBlank(message = "Model is required")
    @Column(nullable = false)
    private String model;

    @Column(name = "date_excluded", nullable = false, updatable = false)
    private LocalDateTime dateExcluded;

    @PrePersist
    protected void onCreate() {
        dateExcluded = LocalDateTime.now();
    }
}
