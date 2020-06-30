package com.example.BookStore.api;

import com.example.BookStore.model.Book;
import com.example.BookStore.service.bookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class bookController {

    @Autowired
    private bookService bookService;

    @GetMapping("/books")
    public List<Book> getBook(){
        return bookService.getBook();
    }

}
