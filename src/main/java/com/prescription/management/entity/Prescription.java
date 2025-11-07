package com.prescription.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "prescriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Prescription date is required")
    @Column(nullable = false)
    private LocalDate prescriptionDate;

    @NotBlank(message = "Patient name is required")
    @Size(min = 2, max = 100, message = "Patient name must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    private String patientName;

    @NotNull(message = "Patient age is required")
    @Min(value = 0, message = "Age must be at least 0")
    @Max(value = 120, message = "Age must not exceed 120")
    @Column(nullable = false)
    private Integer patientAge;

    @NotNull(message = "Patient gender is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Gender patientGender;

    @Size(max = 2000, message = "Diagnosis must not exceed 2000 characters")
    @Column(length = 2000)
    private String diagnosis;

    @Size(max = 2000, message = "Medicines must not exceed 2000 characters")
    @Column(length = 2000)
    private String medicines;

    private LocalDate nextVisitDate;

    @Column(nullable = false, updatable = false)
    private LocalDate createdDate;

    @Column(nullable = false)
    private LocalDate modifiedDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDate.now();
        modifiedDate = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDate.now();
    }
}
