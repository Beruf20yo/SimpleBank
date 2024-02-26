package org.example.web_controller.admin;

import org.example.domain.model.User;
import org.example.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin/user")
    public String showUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("users", userRepository.findAll());
        return "/admin/panel/UserPanel";
    }

    @GetMapping("/admin/user/{id}")
    public String showEditUser(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userRepository.findById(id).get());
        model.addAttribute("users", userRepository.findAll());
        return "/admin/panel/UserPanel";
    }

    @GetMapping("/admin/user_list/{id}")
    public String showUserPage(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userRepository.findById(id).get());
        model.addAttribute("users", userRepository.findAll());
        return "/admin/page/UserPage";
    }

    @PostMapping("/admin/user")
    public ModelAndView saveUser(RedirectAttributes redirectAttributes, @ModelAttribute("user") User user) {
        try {
            userRepository.save(user);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось сохранить запись");
            redirectAttributes.addFlashAttribute("user", user);
            return new ModelAndView("redirect:/admin/user");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Сохранено");
        return new ModelAndView("redirect:/admin/user");
    }

    @DeleteMapping(path = "/admin/user/{id}")
    public ModelAndView deleteUser(RedirectAttributes redirectAttributes, @PathVariable("id") Integer id) {
        User user = userRepository.findById(id).get();
        try {
            userRepository.delete(user);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось удалить запись");
            redirectAttributes.addFlashAttribute("user", user);
            return new ModelAndView("redirect:/admin/user");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Удалено");
        return new ModelAndView("redirect:/admin/user");
    }

}
