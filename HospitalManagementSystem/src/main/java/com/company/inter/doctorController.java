// src/main/java/com/company/varnaa/doctorController.java
package com.company.inter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctors")
public class doctorController {

    @Autowired
    private appointmentService service;

    @Autowired
    private prescriptionService prescriptionService;   // NEW

    @RequestMapping("/doctorAppointments")
    public String showDoctorAppointments(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String doctorName = authentication.getName();

        List<appointment> doctorAppointments = service.findByDoctorName(doctorName);

        // Map: appointmentId -> hasPrescription?
        Map<Integer, Boolean> prescriptionStatus = new HashMap<>();
        for (appointment appt : doctorAppointments) {
            boolean has = prescriptionService.hasPrescription(appt.getAppointment_id());
            prescriptionStatus.put(appt.getAppointment_id(), has);
        }

        model.addAttribute("doctorAppointments", doctorAppointments);
        model.addAttribute("prescriptionStatus", prescriptionStatus); // NEW
        return "doctorAppointments.html";
    }

    @RequestMapping
    public String showDoctorsHome() {
        return "doctors";
    }
}
