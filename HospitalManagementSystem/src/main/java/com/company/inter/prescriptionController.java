package com.company.inter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class prescriptionController {

    @Autowired
    private prescriptionService prescriptionService;

    @Autowired
    private appointmentService appointmentService;

    // called by the prescription form (varsha.html)
    @GetMapping("/savePrescription")
    public String savePrescription(@ModelAttribute("prescription") prescription prescription,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        // make sure appointmentID is present
        if (prescription.getAppointmentID() == null) {
            bindingResult.rejectValue("appointmentID", "error.appointmentID",
                    "Appointment ID is required.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("prescription", prescription);
            return "varsha"; // go back to the form
        }

        // mark that appointment as prescribed (in appointment table)
        Integer appointmentId = prescription.getAppointmentID();
        appointmentService.setPrescription("prescribed", appointmentId);

        // set doctor name from logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String doctorName = auth.getName();
        prescription.setDoctorName(doctorName);

        // save into prescription table
        prescriptionService.save(prescription);

        redirectAttributes.addFlashAttribute("message", "Prescription was successfully saved");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/doctors/doctorAppointments";
    }

    // Patient: "My Prescriptions" page
    @GetMapping("/viewPrescription")
    public String viewPrescription(Model model) {

        // logged-in username (patient)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String patientName = auth.getName();

        // load all prescriptions for this patient
        List<prescription> prescriptions = prescriptionService.findByPatientName(patientName);
        model.addAttribute("prescriptions", prescriptions);

        // must match your template file name under /templates
        // e.g. src/main/resources/templates/viewPrescriotions.html
        return "viewPrescriotions";
    }
}
