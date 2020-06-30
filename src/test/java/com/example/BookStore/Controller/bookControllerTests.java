package com.example.BookStore.Controller;

import com.example.BookStore.api.bookController;
import com.example.BookStore.model.Book;
import com.example.BookStore.service.bookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class bookControllerTests {
    @Autowired
    private bookController bookController;
    @MockBean
    private bookService bookService;

    @Test
    public void getBook(){
        List<Book> bookList = new ArrayList<>();

        Book b1 = new Book();
        b1.setId(2);
        b1.setBook_name("When Never Comes");
        b1.setAuthor_name("Barbara Davis");
        b1.setPrice(179.00);

        Book b2 = new Book();
        b2.setId(1);
        b2.setBook_name("Before We Were Yours: A Novel");
        b2.setAuthor_name("Lisa Wingate");
        b2.setPrice(340.00);

        bookList.add(b1);
        bookList.add(b2);

        when(bookService.getBook()).thenReturn(bookList);
        assertThat(bookController.getBook()).isEqualTo(bookList);
    }

}
