package org.example.web_controller;

import org.example.domain.model.User;
import org.example.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/profile")
    public String profile(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping(value = "/profile")
    public ModelAndView profilePost(RedirectAttributes redirectAttributes, @ModelAttribute("user") User newUser, Model model) {
        User user = userRepository.findByUsername(newUser.getUsername());
        user.setUsername(newUser.getUsername());
        user.setFirstname(newUser.getFirstname());
        user.setLastname(newUser.getLastname());
        user.setMiddleName(newUser.getMiddleName());
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());
        model.addAttribute("user", user);
        try {
            userRepository.save(user);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось сохранить");
            redirectAttributes.addFlashAttribute("user", user);
            return new ModelAndView("redirect:/user/user");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Сохранено");
        return new ModelAndView("redirect:/user/profile");
    }


}