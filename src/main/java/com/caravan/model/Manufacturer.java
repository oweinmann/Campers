package com.caravan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "manufacturers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Brand name is required")
    @Column(nullable = false, unique = true)
    private String brand;

    @NotBlank(message = "Origin is required")
    @Column(nullable = false)
    private String origin; // Australian Made or Imported

    private String website;

    @Column(name = "known_for")
    private String knownFor;

    @Column(name = "key_models")
    private String keyModels;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
