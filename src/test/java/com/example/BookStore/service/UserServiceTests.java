package com.example.BookStore.service;

import com.example.BookStore.model.Order;
import com.example.BookStore.model.User;
import com.example.BookStore.model.UserJoinOrder;
import com.example.BookStore.repository.userRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;
    @MockBean
    private userRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void AddUser(){
        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        user.setPassword("12345678");
        user.setDateOfBirth("01/02/1999");

        userService.AddUser(user);
    }

    @Test
    public void getLogInUser(){
        String username = "hello.world";
        when(userRepository.getLogInUser()).thenReturn(username);
        assertThat(userService.getLoginUser()).isEqualTo(username);
    }

    @Test
    public void getUser(){
        UserJoinOrder userJoinOrder = new UserJoinOrder();
        userJoinOrder.setName("hello");
        userJoinOrder.setSurname("world");
        userJoinOrder.setBooks(Arrays.asList(1,2));
        userJoinOrder.setDate_of_birth("01/02/1985");

        when(userRepository.getLogInUser()).thenReturn(anyString());
        when(userRepository.getUser("hello.world")).thenReturn(userJoinOrder);

        UserJoinOrder result = userService.getUser();
        assertThat(result).isEqualTo(userJoinOrder);
    }

    @Test
    public void addOrder(){
        //Test input
        Order order = new Order();
        order.setOrderList(Arrays.asList(1,2));
        //Test output
        Map<String,Object> test_result = new HashMap<>();
        test_result.put("price",123.123);

        when(userRepository.getLogInUser()).thenReturn("hello.world");
        when(userRepository.addOrder(order,"hello.world")).thenReturn(test_result);

        Map<String,Object> output_result = userService.addOrder(order);
        assertThat(output_result.size()).isEqualTo(1);
    }

    @Test
    public void deleteUser(){
        String username = "hello.world";
        when(userRepository.getLogInUser()).thenReturn(username);
        userService.deleteUser();
    }

    @Test
    public void loginUser(){
        User user = new User();
        user.setUsername("hello.world");
        user.setPassword("12345678");

        userService.login(user);
    }
}
