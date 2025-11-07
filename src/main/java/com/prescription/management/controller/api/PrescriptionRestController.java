package com.prescription.management.controller.api;

import com.prescription.management.dto.PrescriptionDTO;
import com.prescription.management.entity.Prescription;
import com.prescription.management.mapper.PrescriptionMapper;
import com.prescription.management.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/prescriptions")
@RequiredArgsConstructor
public class PrescriptionRestController {

    private final PrescriptionService prescriptionService;
    private final PrescriptionMapper prescriptionMapper;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPrescriptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "prescriptionDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Prescription> prescriptionPage = prescriptionService.getAllPrescriptions(pageable);

        List<PrescriptionDTO> prescriptions = prescriptionPage.getContent().stream()
                .map(prescriptionMapper::toDTO)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("prescriptions", prescriptions);
        response.put("currentPage", prescriptionPage.getNumber());
        response.put("totalItems", prescriptionPage.getTotalElements());
        response.put("totalPages", prescriptionPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDTO> getPrescriptionById(@PathVariable Long id) {
        Prescription prescription = prescriptionService.getPrescriptionById(id);
        return ResponseEntity.ok(prescriptionMapper.toDTO(prescription));
    }
}
