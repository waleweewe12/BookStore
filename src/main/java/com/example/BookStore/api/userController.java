package com.example.BookStore.api;


import com.example.BookStore.model.Order;
import com.example.BookStore.model.User;
import com.example.BookStore.model.UserJoinOrder;
import com.example.BookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RestController
public class userController {

    @Autowired
    private UserService userService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PostMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void AddUser(@Valid @RequestBody User user){
        userService.AddUser(user);
    }

    @GetMapping(path = "/users" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserJoinOrder getUserRecord(){
        return userService.getUser();
    }

    @PostMapping(path="/users/orders" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Object> order(@Valid @RequestBody Order orders){
        return userService.addOrder(orders);
    }

    @DeleteMapping(path = "/users" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUserRecord(){
        userService.deleteUser();
    }

    @PostMapping(path = "/login" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void logIn(@RequestBody User user){
        userService.login(user);
    }

}

