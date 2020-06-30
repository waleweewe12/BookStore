package com.example.BookStore.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Book {

    @JsonProperty("id")
    private int id;
    @JsonProperty("author_name")
    private String author_name;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("book_name")
    private String book_name;
}
