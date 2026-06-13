package com.company.inter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface appointmentRepository extends JpaRepository<appointment, Integer> {

    // Doctor-specific appointments
    List<appointment> findByDoctorName(String doctorName);

    // Patient-specific appointments
    List<appointment> findByPatientName(String patientName);

    // Update appointment confirmation status (used by Receptionist)
    @Modifying
    @Transactional
    @Query("UPDATE appointment a SET a.confirmed = :confirmation WHERE a.appointment_id = :id")
    int setConfirmation(@Param("confirmation") String confirmation, @Param("id") Integer id);

    // Update appointment prescription flag (used by Doctor)
    @Modifying
    @Transactional
    @Query("UPDATE appointment a SET a.prescription = :prescription WHERE a.appointment_id = :id")
    int setPrescription(@Param("prescription") String prescription, @Param("id") Integer id);

    // Fetch appointments for a specific doctor and date ("Today's Appointments")
    @Query(value = "SELECT * FROM appointment a " +
            "WHERE a.appointment_date = :date AND a.doctor_name = :doctorName", nativeQuery = true)
    List<appointment> findByDate(@Param("date") String date,
            @Param("doctorName") String doctorName);
}
