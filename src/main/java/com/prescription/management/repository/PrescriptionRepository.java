package com.prescription.management.repository;

import com.prescription.management.entity.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    Page<Prescription> findByPrescriptionDateBetween(
        LocalDate startDate,
        LocalDate endDate,
        Pageable pageable
    );

    List<Prescription> findByPrescriptionDateBetween(
        LocalDate startDate,
        LocalDate endDate
    );

    @Query("SELECT DAY(p.prescriptionDate) as day, COUNT(p) as count " +
           "FROM Prescription p " +
           "WHERE p.prescriptionDate BETWEEN :startDate AND :endDate " +
           "GROUP BY DAY(p.prescriptionDate) " +
           "ORDER BY DAY(p.prescriptionDate)")
    List<Object[]> getDayWiseCount(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}
