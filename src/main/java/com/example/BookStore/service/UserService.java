package com.example.BookStore.service;


import com.example.BookStore.model.Order;
import com.example.BookStore.model.User;
import com.example.BookStore.model.UserJoinOrder;
import com.example.BookStore.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private userRepository userRepository;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void AddUser(User user){
        userRepository.AddUser(user);
    }

    public String getLoginUser(){
        return userRepository.getLogInUser();
    }

    public UserJoinOrder getUser(){
        String LoginUser = getLoginUser();
        return userRepository.getUser(LoginUser);
    }

    public Map<String,Object> addOrder(Order orders){
        String LogInUser = getLoginUser();
        return userRepository.addOrder(orders,LogInUser);
    }

    public void deleteUser(){
        String LogInUser = getLoginUser();
        userRepository.deleteUser(LogInUser);
    }

    public void login(User user){
        userRepository.login(user);
    }
}
