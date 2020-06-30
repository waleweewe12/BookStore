package com.example.BookStore.Controller;

import com.example.BookStore.api.userController;
import com.example.BookStore.model.Order;
import com.example.BookStore.model.User;
import com.example.BookStore.model.UserJoinOrder;
import com.example.BookStore.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class userControllerTest {

    @Autowired
    private userController userController;
    @MockBean
    private UserService userService;

    @Test
    public void AddUser(){
        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        user.setPassword("12345678");
        user.setDateOfBirth("12/02/1999");

        userController.AddUser(user);
    }

    @Test
    public void getUserRecord(){
        UserJoinOrder userJoinOrder = new UserJoinOrder();
        userJoinOrder.setName("hello");
        userJoinOrder.setSurname("world");
        userJoinOrder.setBooks(Arrays.asList(1,2));
        userJoinOrder.setDate_of_birth("01/02/1985");

        when(userService.getUser()).thenReturn(userJoinOrder);
        assertThat(userController.getUserRecord()).isEqualTo(userJoinOrder);
    }

    @Test
    public void order(){
        //Test input
        Order order = new Order();
        order.setOrderList(Arrays.asList(1,2));
        //Test result
        Map<String,Object> result = new HashMap<>();
        result.put("price",123.123);

        when(userService.addOrder(order)).thenReturn(result);
        assertThat(userController.order(order).size()).isEqualTo(1);
    }

    @Test
    public void deleteUserRecord(){
        userController.deleteUserRecord();
    }

    @Test
    public void login(){
        User user = new User();
        user.setUsername("hello.world");
        user.setPassword("12345678");

        userController.logIn(user);
    }

}
