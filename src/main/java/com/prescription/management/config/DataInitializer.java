package com.prescription.management.config;

import com.prescription.management.entity.Gender;
import com.prescription.management.entity.Prescription;
import com.prescription.management.entity.User;
import com.prescription.management.repository.PrescriptionRepository;
import com.prescription.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initializeUsers();
        initializeSamplePrescriptions();
    }

    private void initializeUsers() {
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role("USER")
                    .enabled(true)
                    .build();

            User doctor = User.builder()
                    .username("doctor")
                    .password(passwordEncoder.encode("doctor123"))
                    .role("USER")
                    .enabled(true)
                    .build();

            userRepository.save(admin);
            userRepository.save(doctor);

            System.out.println("Demo users created:");
            System.out.println("Username: admin | Password: admin123");
            System.out.println("Username: doctor | Password: doctor123");
        }
    }

    private void initializeSamplePrescriptions() {
        if (prescriptionRepository.count() == 0) {
            LocalDate today = LocalDate.now();

            prescriptionRepository.save(Prescription.builder()
                    .prescriptionDate(today.minusDays(5))
                    .patientName("John Smith")
                    .patientAge(45)
                    .patientGender(Gender.MALE)
                    .diagnosis("Hypertension")
                    .medicines("Amlodipine 5mg - Once daily, Metoprolol 50mg - Twice daily")
                    .nextVisitDate(today.plusDays(30))
                    .build());

            prescriptionRepository.save(Prescription.builder()
                    .prescriptionDate(today.minusDays(3))
                    .patientName("Sarah Johnson")
                    .patientAge(32)
                    .patientGender(Gender.FEMALE)
                    .diagnosis("Type 2 Diabetes")
                    .medicines("Metformin 500mg - Twice daily after meals")
                    .nextVisitDate(today.plusDays(90))
                    .build());

            prescriptionRepository.save(Prescription.builder()
                    .prescriptionDate(today.minusDays(2))
                    .patientName("Michael Brown")
                    .patientAge(58)
                    .patientGender(Gender.MALE)
                    .diagnosis("Arthritis")
                    .medicines("Ibuprofen 400mg - Three times daily, Glucosamine 500mg - Once daily")
                    .nextVisitDate(today.plusDays(60))
                    .build());

            prescriptionRepository.save(Prescription.builder()
                    .prescriptionDate(today.minusDays(1))
                    .patientName("Emily Davis")
                    .patientAge(28)
                    .patientGender(Gender.FEMALE)
                    .diagnosis("Seasonal Allergies")
                    .medicines("Cetirizine 10mg - Once daily at bedtime")
                    .nextVisitDate(today.plusDays(14))
                    .build());

            prescriptionRepository.save(Prescription.builder()
                    .prescriptionDate(today)
                    .patientName("Robert Wilson")
                    .patientAge(65)
                    .patientGender(Gender.MALE)
                    .diagnosis("High Cholesterol")
                    .medicines("Atorvastatin 20mg - Once daily at night")
                    .nextVisitDate(today.plusDays(180))
                    .build());

            System.out.println("Sample prescriptions created: 5 records");
        }
    }
}
