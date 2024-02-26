package org.example.web_controller.admin;

import org.example.domain.model.Appointment;
import org.example.domain.repository.AppointmentRepository;
import org.example.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin/appointment")
    public String showAppointment(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("appointments", appointmentRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "/admin/panel/AppointmentPanel";
    }

    @GetMapping("/admin/appointment/{id}")
    public String showEditAppointment(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("appointment", appointmentRepository.findById(id).get());
        model.addAttribute("appointments", appointmentRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "/admin/panel/AppointmentPanel";
    }

    @PostMapping("/admin/appointment")
    public ModelAndView saveAppointment(RedirectAttributes redirectAttributes, @ModelAttribute("appointment") Appointment appointment) {
        try {
            appointmentRepository.save(appointment);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось сохранить запись");
            redirectAttributes.addFlashAttribute("appointment", appointment);
            return new ModelAndView("redirect:/admin/appointment");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Сохранено");
        return new ModelAndView("redirect:/admin/appointment");
    }

    @DeleteMapping(path = "/admin/appointment/{id}")
    public ModelAndView deleteAppointment(RedirectAttributes redirectAttributes, @PathVariable("id") Integer id) {
        Appointment appointment = appointmentRepository.findById(id).get();
        try {
            appointmentRepository.delete(appointment);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось удалить запись");
            redirectAttributes.addFlashAttribute("appointment", appointment);
            return new ModelAndView("redirect:/admin/appointment");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Удалено");
        return new ModelAndView("redirect:/admin/appointment");
    }

}
