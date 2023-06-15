package com.example.springbootmybatis.controller;

import com.example.springbootmybatis.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public Object getAccountList() {
        return accountService.findAll();
    }

    @PostMapping
    public void addAccount(@RequestBody AccountDTO accountDTO) {
        accountService.addAccount(accountDTO);
    }

}
