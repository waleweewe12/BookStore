package com.example.BookStore.service;

import com.example.BookStore.model.Book;
import com.example.BookStore.repository.bookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class bookServiceTests {

    @Autowired
    private bookService bookService;
    @MockBean
    private bookRepository bookRepository;

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

        Mockito.when(bookRepository.getBook()).thenReturn(bookList);
        List <Book> books = bookService.getBook();
        assertThat(books.size()).isNotEqualTo(0);
    }

    @Test
    public void pullBook(){
        bookService.pullBook();
    }

}
