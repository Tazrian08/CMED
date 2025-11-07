package com.prescription.management.controller.web;

import com.prescription.management.dto.DayCountDTO;
import com.prescription.management.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final PrescriptionService prescriptionService;

    @GetMapping("/daywise")
    public String showDayWiseReport(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model
    ) {
        LocalDate now = LocalDate.now();
        int selectedYear = (year != null) ? year : now.getYear();
        int selectedMonth = (month != null) ? month : now.getMonthValue();

        List<DayCountDTO> dayCounts = prescriptionService.getDayWiseCount(selectedYear, selectedMonth);

        model.addAttribute("dayCounts", dayCounts);
        model.addAttribute("selectedYear", selectedYear);
        model.addAttribute("selectedMonth", selectedMonth);
        model.addAttribute("monthName", YearMonth.of(selectedYear, selectedMonth).getMonth().name());

        return "report-daywise";
    }
}
