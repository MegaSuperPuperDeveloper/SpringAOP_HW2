package com.example.springaop_hw2.service;

import com.example.springaop_hw2.model.Account;
import com.example.springaop_hw2.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public Account createAccount(String login, String password, Double balance) {
        return accountRepository.save(new Account(login, password, balance));
    }

    public void deleteAccountById(Long id) {
        accountRepository.deleteById(id);
    }

    public Account updatePassword(Long id, String password) {
        return accountRepository.updatePasswordById(id, password);
    }

    public Account updateLogin(Long id, String login) {
        return accountRepository.updateLoginById(id, login);
    }

    public Account updateBalance(Long id, Double balance) {
        return accountRepository.updateBalanceById(id, balance);
    }

    @Transactional
    public List<Account> sendMoney(Long idSender, Long idReceiver, Double amount) throws IllegalAccessException {
        if (idSender == null || idReceiver == null || amount == null || amount <= 10) {
            throw new IllegalAccessException("IdSender, IdReceiver and Amount are required");
        }

        if (!accountRepository.existsById(idSender) || !accountRepository.existsById(idReceiver)) {
            throw new IllegalAccessException("Account(s) isn't/aren't exist");
        }

        Account sender = accountRepository.findById(idSender).get();
        Account receiver = accountRepository.findById(idReceiver).get();

        if (sender.getBalance() < amount) {
            throw new IllegalAccessException("Sender does not have enough money");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        accountRepository.updateBalanceById(idSender, sender.getBalance());
        accountRepository.updateBalanceById(idReceiver, receiver.getBalance());

        List<Account> accounts = new ArrayList<>();
        accounts.add(sender);
        accounts.add(receiver);

        return accounts;
    }

}