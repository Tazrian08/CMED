package com.prescription.management.controller.web;

import com.prescription.management.entity.Prescription;
import com.prescription.management.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.YearMonth;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final PrescriptionService prescriptionService;

    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        LocalDate start;
        LocalDate end;

        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            start = LocalDate.parse(startDate);
            end = LocalDate.parse(endDate);
        } else {
            YearMonth currentMonth = YearMonth.now();
            start = currentMonth.atDay(1);
            end = currentMonth.atEndOfMonth();
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "prescriptionDate"));
        Page<Prescription> prescriptionPage = prescriptionService.getPrescriptionsByDateRange(start, end, pageable);

        model.addAttribute("prescriptions", prescriptionPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prescriptionPage.getTotalPages());
        model.addAttribute("totalItems", prescriptionPage.getTotalElements());
        model.addAttribute("startDate", start.toString());
        model.addAttribute("endDate", end.toString());

        return "dashboard";
    }
}
