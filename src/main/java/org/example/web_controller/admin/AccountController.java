package org.example.web_controller.admin;

import org.example.domain.model.Account;
import org.example.domain.repository.AccountRepository;
import org.example.domain.repository.AccountTypeRepository;
import org.example.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @GetMapping("/admin/account")
    public String showAccount(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("accounts", accountRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("accountTypes", accountTypeRepository.findAll());
        return "/admin/panel/AccountPanel";
    }

    @GetMapping("/admin/account/{id}")
    public String showEditAccount(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("account", accountRepository.findById(id).get());
        model.addAttribute("accounts", accountRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("accountTypes", accountTypeRepository.findAll());
        return "/admin/panel/AccountPanel";
    }

    @GetMapping("/admin/account_list/{id}")
    public String showAccountPage(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("account", accountRepository.findById(id).get());
        model.addAttribute("accounts", accountRepository.findAll());
        model.addAttribute("accountTypes", accountTypeRepository.findAll());
        return "/admin/page/AccountPage";
    }

    @PostMapping("/admin/account")
    public ModelAndView saveAccount(RedirectAttributes redirectAttributes, @ModelAttribute("account") Account account) {
        try {
            accountRepository.save(account);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось сохранить запись");
            redirectAttributes.addFlashAttribute("account", account);
            return new ModelAndView("redirect:/admin/account");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Сохранено");
        return new ModelAndView("redirect:/admin/account");
    }

    @DeleteMapping(path = "/admin/account/{id}")
    public ModelAndView deleteAccount(RedirectAttributes redirectAttributes, @PathVariable("id") Integer id) {
        Account account = accountRepository.findById(id).get();
        try {
            accountRepository.delete(account);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось удалить запись");
            redirectAttributes.addFlashAttribute("account", account);
            return new ModelAndView("redirect:/admin/account");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Удалено");
        return new ModelAndView("redirect:/admin/account");
    }

}
