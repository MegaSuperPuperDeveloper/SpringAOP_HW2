package com.example.springaop_hw2;

import com.example.springaop_hw2.model.Account;
import com.example.springaop_hw2.model.enums.Role;
import com.example.springaop_hw2.repository.AccountRepository;
import com.example.springaop_hw2.service.AccountService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAopHw2Application {

    public static void main(String[] args) {
        AccountRepository accountRepository = SpringApplication.run(SpringAopHw2Application.class, args).getBean(AccountRepository.class);
        accountRepository.save(new Account("admin", "admin", 100d, Role.ROLE_ADMIN));
    }

}