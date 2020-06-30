package com.example.BookStore.model;

import lombok.Data;

import java.util.List;

@Data
public class UserJoinOrder {
    private String name;
    private String surname;
    private String date_of_birth;
    private List<Integer> books;
}
