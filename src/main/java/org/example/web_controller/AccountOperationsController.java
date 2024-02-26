package org.example.web_controller;

import org.example.domain.model.User;
import org.example.domain.repository.AccountRepository;
import org.example.domain.repository.UserRepository;
import org.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/account")
public class AccountOperationsController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public String showEditUser(Principal principal, Model model, @PathVariable("id") Integer id) {
        User user = userRepository.findByUsername(principal.getName());
        if (!user.getAccountList().contains(accountRepository.findById(id).get())) {
            return ("redirect:/home");
        }
        model.addAttribute("account", accountRepository.findById(id).get());
        return "account";
    }

    @GetMapping(value = "/deposit")
    public String deposit(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("accounts", user.getAccountList());
        model.addAttribute("amount", "0");
        return "deposit";
    }

    @PostMapping(value = "/deposit")
    public ModelAndView depositPOST(RedirectAttributes redirectAttributes,
                                    @ModelAttribute("amount") String amount,
                                    @ModelAttribute("accountId") Integer accountId,
                                    Principal principal) {
        try {
            accountService.deposit(accountId, Double.parseDouble(amount), principal);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось выполнить операцию");
            return new ModelAndView("redirect:/account/deposit");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Выполнено");
        return new ModelAndView("redirect:/account/deposit");
    }

    @GetMapping(value = "/withdraw")
    public String withdraw(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("accounts", user.getAccountList());
        model.addAttribute("amount", "0");
        return "withdraw";
    }

    @PostMapping(value = "/withdraw")
    public ModelAndView withdrawPOST(RedirectAttributes redirectAttributes,
                                     @ModelAttribute("amount") String amount,
                                     @ModelAttribute("accountId") Integer accountId,
                                     Principal principal) {
        try {
            accountService.withdraw(accountId, Double.parseDouble(amount), principal);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось выполнить операцию");
            return new ModelAndView("redirect:/account/withdraw");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Выполнено");
        return new ModelAndView("redirect:/account/withdraw");
    }

    @GetMapping(value = "/transfer")
    public String transfer(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("accounts", user.getAccountList());
        model.addAttribute("allAccounts", accountRepository.findAll());
        model.addAttribute("amount", "0");
        return "transfer";
    }

    @PostMapping(value = "/transfer")
    public ModelAndView transferPOST(RedirectAttributes redirectAttributes,
                                     @ModelAttribute("amount") String amount,
                                     @ModelAttribute("accountId") Integer accountId,
                                     @ModelAttribute("toAccountId") Integer toAccountId,
                                     Principal principal) {
        try {
            accountService.transfer(accountId, toAccountId, Double.parseDouble(amount), principal);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось выполнить операцию");
            return new ModelAndView("redirect:/account/transfer");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Выполнено");
        return new ModelAndView("redirect:/account/transfer");
    }

}