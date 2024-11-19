package com.example.springaop_hw2.controller;

import com.example.springaop_hw2.model.Account;
import com.example.springaop_hw2.model.enums.Role;
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

    /**
     * Method is required to return list of all users
     * @return list of all users
     */
    @GetMapping
    public ResponseEntity<Iterable<Account>> getAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    /**
     * Method is required to return user using a user id
     * @param id user id of the person who needs to be returned
     * @return user using user id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Account>> getAccount(@PathVariable Long id) {
        if (accountService.getAccountById(id).isEmpty()) {
            return new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.OK);
    }

    /**
     * Method is required to create a new user
     * @param login new user login
     * @param password new user password
     * @param balance new user balance
     * @return new user
     */
    @PostMapping("/{login}/{password}/{balance}")
    public ResponseEntity<Account> createAccount(@PathVariable String login, @PathVariable String password, @PathVariable Double balance) {
        return new ResponseEntity<>(accountService.createAccount(login, password, balance, Role.ROLE_USER), HttpStatus.CREATED);
    }

    /**
     * Method is required to delete user using a user id
     * @param id user id of the person who needs to be deleted
     * @return only status OK or NOT_FOUND
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        if (accountService.getAccountById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountService.deleteAccountById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method is required to change user's login using a user id
     * @param id user id of the person who is changing their login
     * @param login new login
     * @return only status ok
     */
    @PatchMapping("/{id}/l/{login}")
    public ResponseEntity<Void> updateLogin(@PathVariable Long id, @PathVariable String login) {
        if (accountService.getAccountById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountService.updateLogin(id, login);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method is required to change user's password using a user id
     * @param id user id of the person who is changing their password
     * @param password new password
     * @return only status ok.
     */
    @PatchMapping("/{id}/p/{password}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @PathVariable String password) {
        if (accountService.getAccountById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountService.updatePassword(id, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method is required for transaction between two users using a user id
     * @return list members of transaction
     */
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