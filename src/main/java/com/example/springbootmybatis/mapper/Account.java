package com.example.springbootmybatis.mapper;

import lombok.Data;

@Data
public class Account {
    private int id;
    private String username;
    private String password;
    private String nickName;
    private int age;
    private String location;
}
