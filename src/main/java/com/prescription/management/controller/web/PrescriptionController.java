package com.prescription.management.controller.web;

import com.prescription.management.entity.Gender;
import com.prescription.management.entity.Prescription;
import com.prescription.management.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("prescription", new Prescription());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("isEdit", false);
        return "prescription-form";
    }

    @PostMapping
    public String createPrescription(
            @Valid @ModelAttribute("prescription") Prescription prescription,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genders", Gender.values());
            model.addAttribute("isEdit", false);
            return "prescription-form";
        }

        prescriptionService.createPrescription(prescription);
        redirectAttributes.addFlashAttribute("successMessage", "Prescription created successfully");
        return "redirect:/dashboard";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Prescription prescription = prescriptionService.getPrescriptionById(id);
        model.addAttribute("prescription", prescription);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("isEdit", true);
        return "prescription-form";
    }

    @PostMapping("/{id}")
    public String updatePrescription(
            @PathVariable Long id,
            @Valid @ModelAttribute("prescription") Prescription prescription,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genders", Gender.values());
            model.addAttribute("isEdit", true);
            return "prescription-form";
        }

        prescriptionService.updatePrescription(id, prescription);
        redirectAttributes.addFlashAttribute("successMessage", "Prescription updated successfully");
        return "redirect:/dashboard";
    }

    @PostMapping("/{id}/delete")
    public String deletePrescription(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        prescriptionService.deletePrescription(id);
        redirectAttributes.addFlashAttribute("successMessage", "Prescription deleted successfully");
        return "redirect:/dashboard";
    }

    @GetMapping("/{id}/view")
    public String viewPrescription(@PathVariable Long id, Model model) {
        Prescription prescription = prescriptionService.getPrescriptionById(id);
        model.addAttribute("prescription", prescription);
        return "prescription-view";
    }
}
