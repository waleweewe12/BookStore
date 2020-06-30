package com.example.BookStore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class User {

    private String name;
    private String surname;

    @NotNull
    @JsonProperty("username")
    private String username;

    @NotNull
    @Size(min = 6, max = 30)
    @JsonProperty("password")
    private String password;

    @NotNull
    @JsonProperty("date_of_birth")
    private String DateOfBirth;

}
