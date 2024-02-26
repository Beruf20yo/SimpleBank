package org.example.web_controller;

import org.example.domain.model.Account;
import org.example.domain.model.Bill;
import org.example.domain.model.User;
import org.example.domain.repository.AccountRepository;
import org.example.domain.repository.BillRepository;
import org.example.domain.repository.UserRepository;
import org.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class UsersBillController {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/user/bill")
    public String transfer(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("bill", new Bill());
        model.addAttribute("bills", user.getRecipientsBills());
        List<Account> diff = (List<Account>) accountRepository.findAll();
        diff.removeAll(user.getAccountList());
        model.addAttribute("accounts", diff);
        model.addAttribute("users", userRepository.findAll());
        return "bill";
    }

    @PostMapping("/user/bill")
    public ModelAndView saveBill(Principal principal, RedirectAttributes redirectAttributes, @ModelAttribute("bill") Bill bill) {
        User user = userRepository.findByUsername(principal.getName());
        try {
            bill.setCreator(user);
            billRepository.save(bill);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось создать");
            redirectAttributes.addFlashAttribute("bill", bill);
            return new ModelAndView("redirect:/user/bill");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Сохранено");
        return new ModelAndView("redirect:/user/bill");
    }

    @GetMapping("/user/bill/{id}")
    public String showBill(Principal principal, Model model, @PathVariable("id") Integer id) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("bill", billRepository.findById(id).get());
        model.addAttribute("accounts", user.getAccountList());
        return "/billPayment";
    }

    @PostMapping("/user/bill/{id}")
    public ModelAndView payBill(RedirectAttributes redirectAttributes,
                                @PathVariable("id") Integer id,
                                @ModelAttribute("accountId") Integer accountId,
                                Principal principal) {
        try {
            Bill bill = billRepository.findById(id).get();
            accountService.pay(accountId, bill, principal);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось выполнить операцию");
            return new ModelAndView("redirect:/user/bill");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Выполнено");
        return new ModelAndView("redirect:/user/bill");
    }

}
