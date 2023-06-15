package com.example.springbootmybatis.service;

import com.example.springbootmybatis.mapper.Account;
import com.example.springbootmybatis.mapper.AccountMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountMapper accountMapper;

    public AccountService(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public List<Account> findAll() {
        return accountMapper.findAll();
    }
}
