package com.example.springaop_hw2.controller;

import com.example.springaop_hw2.model.Account;
import com.example.springaop_hw2.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<Iterable<Account>> getAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Account>> getAccount(@PathVariable Long id) {
        if (accountService.getAccountById(id).isEmpty()) {
            return new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.OK);
    }

    @PostMapping("/{login}/{password}/{balance}")
    public ResponseEntity<Account> createAccount(@PathVariable String login, @PathVariable String password, @PathVariable Double balance) {
        return new ResponseEntity<>(accountService.createAccount(login, password, balance), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        if (accountService.getAccountById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountService.deleteAccountById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/l/{login}")
    public ResponseEntity<Void> updateLogin(@PathVariable Long id, @PathVariable String login) {
        if (accountService.getAccountById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountService.updateLogin(id, login);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/p/{password}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @PathVariable String password) {
        if (accountService.getAccountById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountService.updatePassword(id, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{idSender}/{idReceiver}/send/{balance}")
    public ResponseEntity<List<Optional<Account>>> updateBalance(
            @PathVariable Long idSender,
            @PathVariable Long idReceiver,
            @PathVariable Double balance) throws IllegalAccessException {

        if (accountService.getAccountById(idSender).isEmpty() || accountService.getAccountById(idReceiver).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (balance <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        accountService.sendMoney(idSender, idReceiver, balance);

        List<Optional<Account>> accounts = List.of(accountService.getAccountById(idSender), accountService.getAccountById(idReceiver));

        return new ResponseEntity<>(accounts, HttpStatus.OK);

    }

}