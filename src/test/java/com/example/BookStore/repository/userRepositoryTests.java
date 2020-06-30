package com.example.BookStore.repository;

import com.example.BookStore.model.Order;
import com.example.BookStore.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class userRepositoryTests {
    @Autowired
    private userRepository userRepository;

    @Test
    public void AddUser(){
        String uuid = UUID.randomUUID().toString()+"."+"Tests";
        User user = new User();
        user.setUsername(uuid);
        user.setPassword("12345678");
        user.setDateOfBirth("12/02/1999");

        userRepository.AddUser(user);
    }

    @Test
    public void AddUser_Fail(){
        User user_fail1 = new User();
        user_fail1.setUsername("hello.world");
        user_fail1.setPassword("12345678");
        user_fail1.setDateOfBirth("12/02/1999");

        userRepository.AddUser(user_fail1);

        User user_fail2 = new User();
        user_fail2.setUsername("helloWorld");
        user_fail2.setPassword("12345678");
        user_fail2.setDateOfBirth("12/02/1999");

        userRepository.AddUser(user_fail2);
    }

    @Test
    public void getUser(){
        String LoginUser = "hello.world";
        userRepository.getUser(LoginUser);
    }

    @Test
    public void addOrder(){
        Order orders = new Order();
        orders.setOrderList(Arrays.asList(1,2));
        String LogInUser = "hello.world";
        userRepository.addOrder(orders,LogInUser);
    }

    @Test
    public void addOrder_Fail(){
        Order orders = new Order();
        orders.setOrderList(Arrays.asList(1,1822));
        String LogInUser = "hello.world";
        userRepository.addOrder(orders,LogInUser);
    }

    @Test
    public void deleteUser(){
        String LogInUser = "hello.world";
        userRepository.deleteUser(LogInUser);
    }

    @Test
    public void login(){
        User user = new User();
        user.setUsername("hello.world");
        user.setPassword("12345678");
        userRepository.login(user);
    }

    @Test
    public void login_fail(){
        User user = new User();
        user.setUsername("hello.world");
        user.setPassword("1234567");
        userRepository.login(user);
    }
}
