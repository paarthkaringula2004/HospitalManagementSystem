package com.company.inter;

import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

	@GetMapping({ "/", "/main" })
	public String showMain() {
		return "main";
	}

	@GetMapping("/patients")
	public String showPatient(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);
		// String confirmation ="Your appointment has been successfully booked. ID=";
		// model.addAttribute("confirmation",confirmation);
		String id = (String) model.asMap().get("appointmentId");
		model.addAttribute("appointmentId", id);
		return "patients";
	}

	@GetMapping("/doctors")
	public String showDoctors(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);
		return "doctors";
	}

	@GetMapping("/login")
	public String login() {
		return "login"; // login.html
	}

	@GetMapping("/showPostLogin")
	public String showPostLogin(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getAuthorities() == null) {
			return "redirect:/login";
		}

		boolean isPatient = authentication.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_PATIENT"));

		boolean isDoctor = authentication.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR"));

		boolean isReceptionist = authentication.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_RECEPTIONIST"));

		if (isPatient) {
			model.addAttribute("portalName", "Patient Portal");
			model.addAttribute("portalUrl", "/patients/myAppointments");
		} else if (isDoctor) {
			model.addAttribute("portalName", "Doctor Portal");
			model.addAttribute("portalUrl", "/doctors/doctorAppointments");
		} else if (isReceptionist) {
			model.addAttribute("portalName", "Receptionist Portal");
			model.addAttribute("portalUrl", "/receptionist/receptionistAppointments");
		} else {
			// fallback
			model.addAttribute("portalName", "Main Home");
			model.addAttribute("portalUrl", "/");
		}

		return "postlogin"; // still using postlogin.html, but now with role data
	}

	@GetMapping("/receptionist")
	public String showReceptionist(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);

		return "receptionist";
	}

	@Autowired
	private appointmentService service;

	@GetMapping("/add")
	public String newAppointment(Model model) {
		appointment appointment = new appointment();
		appointment.setConfirmed("Not yet confirmed");
		model.addAttribute("appointment", appointment);
		return "add.html";
	}

	@GetMapping("/save")
	public String saveProduct(@ModelAttribute("appointment") appointment appointment,
			BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {

		// get logged-in username
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		// always store this as the patient name
		appointment.setPatientName(username);
		appointment.setConfirmed("Not yet confirmed");

		service.save(appointment);

		String appointmentId = appointment.getAppointment_id().toString();
		String message = "Appointment was successfully booked, your id is: " + appointmentId;
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		redirectAttributes.addFlashAttribute("appointmentId", appointmentId);

		return "redirect:/patients/myAppointments";
	}

	@GetMapping("/cancel")
	public String cancel(@ModelAttribute("appointment") appointment appointment,
			BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {
		Integer id = appointment.getAppointment_id();
		service.delete(id);
		String message = "Appointment was successfully canceled!";
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/patients/myAppointments";
	}

	@RequestMapping("/confirm")
	public String confirm(@ModelAttribute("appointment") appointment appointment, BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {
		System.out.println(appointment);
		// Optional<com.company.varnaa.appointment> x =
		// service.get(appointment.getAppointment_id());
		String confirmation = "confirmed";
		Integer id = appointment.getAppointment_id();
		service.setConfirmation(confirmation, id);
		System.out.println(id);
		String message = "Appointment was successfully confirmed!";
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/receptionist/receptionistAppointments";

	}

	@GetMapping("/confirmm")
	public String showConfirmm(Model model) {
		appointment confirmation = new appointment();
		model.addAttribute("confirmation", confirmation);
		return "confirm";
	}

	@GetMapping("/findbystart")
	public String showByDate(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String doctorName = auth.getName();

		// Get today's date in the same format used in DB (yyyy-MM-dd)
		LocalDate today = LocalDate.now();
		String todayString = today.toString(); // e.g., "2025-11-09"

		// Log it (optional)
		System.out.println("Fetching today's appointments for doctor: " + doctorName + " on " + todayString);

		// Fetch all appointments for this doctor and today
		List<appointment> appointments = service.findByDate(todayString, doctorName);

		// Add to model for Thymeleaf
		model.addAttribute("listAppointments", appointments);

		return "findbystart";
	}

	@GetMapping("/varsha")
	public String createPrescription(@RequestParam("appointmentID") Integer appointmentID,
			@RequestParam("patientName") String patientName,
			Model model) {

		prescription ps = new prescription();
		ps.setAppointmentID(appointmentID);
		ps.setPatientName(patientName);

		model.addAttribute("prescription", ps);
		return "varsha";
	}

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@GetMapping("/signupp")
	public String signupp(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "Account created successfully");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/";
	}

	@Autowired
	public invoiceservice invoiceService;

	@GetMapping("/saveInvoice")
	public String saveInvoice(@ModelAttribute("invoice") invoice invoice,
			BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {
		invoiceService.save(invoice);
		String message = "Invoice was successfully created!";
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/patients";
	}

}
