package org.example.web_controller.admin;

import org.example.domain.model.Bill;
import org.example.domain.repository.AccountRepository;
import org.example.domain.repository.BillRepository;
import org.example.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BillController {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/admin/bill")
    public String showBill(Model model) {
        model.addAttribute("bill", new Bill());
        model.addAttribute("bills", billRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("accounts", accountRepository.findAll());
        return "/admin/panel/BillPanel";
    }

    @GetMapping("/admin/bill/{id}")
    public String showEditBill(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("bill", billRepository.findById(id).get());
        model.addAttribute("bills", billRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("accounts", accountRepository.findAll());
        return "/admin/panel/BillPanel";
    }
    @PostMapping("/admin/bill")
    public ModelAndView saveBill(RedirectAttributes redirectAttributes, @ModelAttribute("bill") Bill bill) {
        try {
            billRepository.save(bill);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось сохранить запись");
            redirectAttributes.addFlashAttribute("bill", bill);
            return new ModelAndView("redirect:/admin/bill");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Сохранено");
        return new ModelAndView("redirect:/admin/bill");
    }

    @DeleteMapping(path = "/admin/bill/{id}")
    public ModelAndView deleteBill(RedirectAttributes redirectAttributes, @PathVariable("id") Integer id) {
        Bill bill = billRepository.findById(id).get();
        try {
            billRepository.delete(bill);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось удалить запись");
            redirectAttributes.addFlashAttribute("bill", bill);
            return new ModelAndView("redirect:/admin/bill");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Удалено");
        return new ModelAndView("redirect:/admin/bill");
    }

}
