package com.example.springbootmybatis.service;

import com.example.springbootmybatis.controller.AccountDTO;
import com.example.springbootmybatis.mapper.Account;
import com.example.springbootmybatis.mapper.AccountMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    public void addAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setAge(accountDTO.getAge());
        account.setLocation(accountDTO.getLocation());
        account.setUsername(accountDTO.getUsername());
        account.setPassword(accountDTO.getPassword());
        account.setNickName(accountDTO.getNickName());
        accountMapper.add(account);
    }

    public Object findByPages(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Account> accounts = accountMapper.findAll();
        PageInfo<Account> pageInfo = new PageInfo<>(accounts);
        return pageInfo;
    }
}
