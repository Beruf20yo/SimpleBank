package org.example.web_controller.admin;

import org.example.domain.model.AccountType;
import org.example.domain.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountTypeController {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @GetMapping("/admin/accountType")
    public String showAccountType(Model model) {
        model.addAttribute("accountType", new AccountType());
        model.addAttribute("accountTypes", accountTypeRepository.findAll());
        return "/admin/panel/AccountTypePanel";
    }

    @GetMapping("/admin/accountType/{id}")
    public String showEditAccountType(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("accountType", accountTypeRepository.findById(id).get());
        model.addAttribute("accountTypes", accountTypeRepository.findAll());
        return "/admin/panel/AccountTypePanel";
    }
    @PostMapping("/admin/accountType")
    public ModelAndView saveAccountType(RedirectAttributes redirectAttributes, @ModelAttribute("accountType") AccountType accountType) {
        try {
            accountTypeRepository.save(accountType);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось сохранить запись");
            redirectAttributes.addFlashAttribute("accountType", accountType);
            return new ModelAndView("redirect:/admin/accountType");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Сохранено");
        return new ModelAndView("redirect:/admin/accountType");
    }

    @DeleteMapping(path = "/admin/accountType/{id}")
    public ModelAndView deleteAccountType(RedirectAttributes redirectAttributes, @PathVariable("id") Integer id) {
        AccountType accountType = accountTypeRepository.findById(id).get();
        try {
            accountTypeRepository.delete(accountType);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось удалить запись");
            redirectAttributes.addFlashAttribute("accountType", accountType);
            return new ModelAndView("redirect:/admin/accountType");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Удалено");
        return new ModelAndView("redirect:/admin/accountType");
    }

}