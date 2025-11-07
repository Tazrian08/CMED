package com.prescription.management.mapper;

import com.prescription.management.dto.PrescriptionDTO;
import com.prescription.management.entity.Prescription;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionMapper {

    public PrescriptionDTO toDTO(Prescription prescription) {
        if (prescription == null) {
            return null;
        }

        return PrescriptionDTO.builder()
                .id(prescription.getId())
                .prescriptionDate(prescription.getPrescriptionDate())
                .patientName(prescription.getPatientName())
                .patientAge(prescription.getPatientAge())
                .patientGender(prescription.getPatientGender())
                .diagnosis(prescription.getDiagnosis())
                .medicines(prescription.getMedicines())
                .nextVisitDate(prescription.getNextVisitDate())
                .build();
    }

    public Prescription toEntity(PrescriptionDTO dto) {
        if (dto == null) {
            return null;
        }

        return Prescription.builder()
                .id(dto.getId())
                .prescriptionDate(dto.getPrescriptionDate())
                .patientName(dto.getPatientName())
                .patientAge(dto.getPatientAge())
                .patientGender(dto.getPatientGender())
                .diagnosis(dto.getDiagnosis())
                .medicines(dto.getMedicines())
                .nextVisitDate(dto.getNextVisitDate())
                .build();
    }
}
