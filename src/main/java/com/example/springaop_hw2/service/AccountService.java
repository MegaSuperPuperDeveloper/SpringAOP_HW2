package com.example.springaop_hw2.service;

import com.example.springaop_hw2.model.Account;
import com.example.springaop_hw2.model.enums.Role;
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

    /**
     * Method is required to create a new user
     * @param login can't be less than 4
     * @param password can't be less than 4
     * @param balance can't be less than 0
     * @param role user's role
     * @return new user
     */
    public Account createAccount(String login, String password, Double balance, Role role) {
        if (login.length() < 4) {
            throw new IllegalArgumentException("Invalid login");
        }
        if (password.length() < 4) {
            throw new IllegalArgumentException("Invalid password");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Invalid balance");
        }
        return accountRepository.save(new Account(login, password, balance, role));
    }

    public void deleteAccountById(Long id) {
        accountRepository.deleteById(id);
    }

    public void updatePassword(Long id, String password) {
        accountRepository.updatePasswordById(id, password);
    }

    public void updateLogin(Long id, String login) {
        accountRepository.updateLoginById(id, login);
    }

    /**
     * Method is required for money transactions using user id
     * @param idSender sender id
     * @param idReceiver receiver id
     * @param amount amount of money
     * @return members of transaction
     * @throws IllegalAccessException
     */
    @Transactional
    public List<Account> sendMoney(Long idSender, Long idReceiver, Double amount) throws IllegalAccessException {
        if (idSender == null || idReceiver == null || amount == null) {
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