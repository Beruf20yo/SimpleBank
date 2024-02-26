package org.example.service;

import jakarta.transaction.Transactional;
import org.example.domain.model.Account;
import org.example.domain.model.Bill;
import org.example.domain.model.Transaction;
import org.example.domain.model.User;
import org.example.domain.repository.AccountRepository;
import org.example.domain.repository.BillRepository;
import org.example.domain.repository.TransactionRepository;
import org.example.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Date;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public void deposit(Integer accountId, double amount, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        if (amount <= 0) {
            throw new RuntimeException();
        }
        Account toAccount = accountRepository.findById(accountId).get();
        toAccount.setAccountBalance(toAccount.getAccountBalance().add(new BigDecimal(amount)));
        accountRepository.save(toAccount);
    }

    @Transactional
    public void withdraw(Integer accountId, double amount, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        if (amount <= 0) {
            throw new RuntimeException();
        }
        Account fromAccount = accountRepository.findById(accountId).get();
        if (new BigDecimal(amount).compareTo(fromAccount.getAccountBalance()) > 0) {
            throw new RuntimeException();
        }
        fromAccount.setAccountBalance(fromAccount.getAccountBalance().subtract(new BigDecimal(amount)));
        accountRepository.save(fromAccount);
    }

    @Transactional
    public void transfer(Integer accountId, Integer toAccountId, double amount, Principal principal) {

        User user = userRepository.findByUsername(principal.getName());

        if (amount <= 0) {
            throw new RuntimeException();
        }

        Account fromAccount = accountRepository.findById(accountId).get();
        Account toAccount = accountRepository.findById(toAccountId).get();
        if (new BigDecimal(amount).compareTo(fromAccount.getAccountBalance()) > 0) {
            throw new RuntimeException();
        }
        fromAccount.setAccountBalance(fromAccount.getAccountBalance().subtract(new BigDecimal(amount)));
        accountRepository.save(fromAccount);
        toAccount.setAccountBalance(toAccount.getAccountBalance().add(new BigDecimal(amount)));
        accountRepository.save(toAccount);

        Date date = new Date(new java.util.Date().getTime());
        Transaction transaction = new Transaction(date, "Вывод средств", new BigDecimal(amount), toAccount, fromAccount);
        transactionRepository.save(transaction);

    }

    @Transactional
    public void pay(Integer accountId, Bill bill, Principal principal) {

        User user = userRepository.findByUsername(principal.getName());

        if (bill.getPrice().compareTo(new BigDecimal(0)) <= 0) {
            throw new RuntimeException();
        }

        Account fromAccount = accountRepository.findById(accountId).get();
        Account toAccount = bill.getAccount();
        if (bill.getPrice().compareTo(fromAccount.getAccountBalance()) > 0) {
            throw new RuntimeException();
        }
        fromAccount.setAccountBalance(fromAccount.getAccountBalance().subtract(bill.getPrice()));
        accountRepository.save(fromAccount);
        toAccount.setAccountBalance(toAccount.getAccountBalance().add(bill.getPrice()));
        accountRepository.save(toAccount);

        Date date = new Date(new java.util.Date().getTime());
        Transaction transaction = new Transaction(date, "Оплата счёта", bill.getPrice(), toAccount, fromAccount);
        transactionRepository.save(transaction);

        bill.setStatus("Оплачено");
        billRepository.save(bill);

    }

}
