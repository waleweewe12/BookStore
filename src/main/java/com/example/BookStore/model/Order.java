package com.example.BookStore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class Order {
    @NotNull
    @JsonProperty("orders")
    private List<Integer> orderList;
    @JsonProperty("username")
    private String username;
    @JsonProperty("id")
    private int id;
}
