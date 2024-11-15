package com.example.springaop_hw2.repository;

import com.example.springaop_hw2.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.login = :login WHERE a.id = :id")
    void updateLoginById(Long id, String login);

    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.password = :password WHERE a.id = :id")
    void updatePasswordById(Long id, String password);

    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.balance = :balance WHERE a.id = :id")
    void updateBalanceById(Long id, Double balance);

}