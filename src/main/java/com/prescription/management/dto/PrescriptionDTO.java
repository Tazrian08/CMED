package com.prescription.management.dto;

import com.prescription.management.entity.Gender;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDTO {
    private Long id;
    private LocalDate prescriptionDate;
    private String patientName;
    private Integer patientAge;
    private Gender patientGender;
    private String diagnosis;
    private String medicines;
    private LocalDate nextVisitDate;
}
