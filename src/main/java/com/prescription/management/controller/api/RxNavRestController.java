package com.prescription.management.controller.api;

import com.prescription.management.service.RxNavService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rxnav")
@RequiredArgsConstructor
public class RxNavRestController {

    private final RxNavService rxNavService;

    @GetMapping("/interactions/{rxcui}")
    public ResponseEntity<String> getDrugInteractions(@PathVariable String rxcui) {
        String interactions = rxNavService.getDrugInteractions(rxcui);
        return ResponseEntity.ok(interactions);
    }
}
