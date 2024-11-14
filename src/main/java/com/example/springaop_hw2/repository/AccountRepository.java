package com.example.springaop_hw2.repository;

import com.example.springaop_hw2.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Modifying
    @Query("UPDATE Account a SET a.login = :login WHERE a.id = :id")
    Account updateLoginById(Long id, String login);

    @Modifying
    @Query("UPDATE Account a SET a.password = :password WHERE a.id = :id")
    Account updatePasswordById(Long id, String password);

    @Modifying
    @Query("UPDATE Account a SET a.balance = :balance WHERE a.id = :id")
    Account updateBalanceById(Long id, Double balance);

}