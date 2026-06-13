package com.company.inter;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface prescriptionRepository extends JpaRepository<prescription, Integer> {

    // Find prescriptions by patient name
    List<prescription> findByPatientName(String patientName);

    // Check if a prescription exists for a given appointment
    boolean existsByAppointmentID(Integer appointmentID);
}
