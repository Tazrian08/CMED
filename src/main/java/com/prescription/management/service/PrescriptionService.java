package com.prescription.management.service;

import com.prescription.management.dto.DayCountDTO;
import com.prescription.management.entity.Prescription;
import com.prescription.management.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    @Transactional
    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    @Transactional
    public Prescription updatePrescription(Long id, Prescription prescription) {
        Prescription existing = prescriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found with id: " + id));

        existing.setPrescriptionDate(prescription.getPrescriptionDate());
        existing.setPatientName(prescription.getPatientName());
        existing.setPatientAge(prescription.getPatientAge());
        existing.setPatientGender(prescription.getPatientGender());
        existing.setDiagnosis(prescription.getDiagnosis());
        existing.setMedicines(prescription.getMedicines());
        existing.setNextVisitDate(prescription.getNextVisitDate());

        return prescriptionRepository.save(existing);
    }

    @Transactional
    public void deletePrescription(Long id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new IllegalArgumentException("Prescription not found with id: " + id);
        }
        prescriptionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<Prescription> getPrescriptionsByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return prescriptionRepository.findByPrescriptionDateBetween(startDate, endDate, pageable);
    }

    @Transactional(readOnly = true)
    public List<Prescription> getPrescriptionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return prescriptionRepository.findByPrescriptionDateBetween(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<DayCountDTO> getDayWiseCount(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Object[]> results = prescriptionRepository.getDayWiseCount(startDate, endDate);

        return results.stream()
                .map(result -> new DayCountDTO((Integer) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<Prescription> getAllPrescriptions(Pageable pageable) {
        return prescriptionRepository.findAll(pageable);
    }
}
