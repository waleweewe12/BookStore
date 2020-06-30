package com.example.BookStore.repository;

import com.example.BookStore.mapper.bookMapper;
import com.example.BookStore.mapper.bookRecommendMapper;
import com.example.BookStore.model.Book;
import com.example.BookStore.model.BookRecommend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class bookRepositoryTests {

    @Autowired
    private bookRepository bookRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void getBook(){
        List<Book> bookList = bookRepository.getBook();
        assertThat(bookList.size()).isNotEqualTo(0);
    }

    @Test
    public void saveBook(){

        //load now book
        String sql = "SELECT * FROM book";
        List<Book> now_book = jdbcTemplate.query(sql,new bookMapper());

        bookRepository.saveBook(now_book);
    }

    @Test
    public void saveBook_Fail(){
        //load now book
        String sql = "SELECT * FROM book";
        List<Book> now_book = jdbcTemplate.query(sql,new bookMapper());

        List<Book> bookList = new ArrayList<>();

        Book b1 = new Book();
        b1.setId(1);
        b1.setBook_name("When Never Comes");
        b1.setAuthor_name("Barbara Davis");
        b1.setPrice(179.00);

        Book b2 = new Book();
        b2.setId(1); // same id
        b2.setBook_name("Before We Were Yours: A Novel");
        b2.setAuthor_name("Lisa Wingate");
        b2.setPrice(340.00);

        bookList.add(b1);
        bookList.add(b2);

        bookRepository.saveBook(bookList);
        bookRepository.saveBook(now_book);
    }

    @Test
    public void saveBookRecommend(){
        //now bookRecommend
        String sql = "SELECT * FROM book_recommend";
        List<BookRecommend> bookRecommendList_now = jdbcTemplate.query(sql,new bookRecommendMapper());

        List<Book> bookList = new ArrayList<>();

        for(BookRecommend b : bookRecommendList_now){
            Book book = new Book();
            book.setId(b.getId());
            bookList.add(book);
        }

        bookRepository.saveBookRecommend(bookList);
    }

    @Test
    public void saveRecommendBook_Fail(){
        //now bookRecommend
        String sql = "SELECT * FROM book_recommend";
        List<BookRecommend> bookRecommendList_now = jdbcTemplate.query(sql,new bookRecommendMapper());

        List<Book> bookList_now = new ArrayList<>();

        for(BookRecommend b : bookRecommendList_now){
            Book book = new Book();
            book.setId(b.getId());
            bookList_now.add(book);
        }

        List<Book> bookList_fail = new ArrayList<>();

        Book b1 = new Book();
        b1.setId(1);
        b1.setBook_name("When Never Comes");
        b1.setAuthor_name("Barbara Davis");
        b1.setPrice(179.00);

        Book b2 = new Book();
        b2.setId(1); // same id
        b2.setBook_name("Before We Were Yours: A Novel");
        b2.setAuthor_name("Lisa Wingate");
        b2.setPrice(340.00);

        bookList_fail.add(b1);
        bookList_fail.add(b2);

        bookRepository.saveBookRecommend(bookList_fail);
        bookRepository.saveBookRecommend(bookList_now);
    }
}
