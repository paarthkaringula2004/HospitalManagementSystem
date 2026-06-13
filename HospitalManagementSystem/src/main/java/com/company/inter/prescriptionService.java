// src/main/java/com/company/varnaa/prescriptionService.java
package com.company.inter;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class prescriptionService {

    @Autowired
    private prescriptionRepository repository;

    public void save(prescription Prescription) {
        repository.save(Prescription);
    }

    public List<prescription> findByPatientName(String patientName) {
        return repository.findByPatientName(patientName);
    }

    // NEW
    public boolean hasPrescription(Integer appointmentId) {
        return repository.existsByAppointmentID(appointmentId);
    }
}
