package com.example.BookStore.service;

import com.example.BookStore.model.Book;
import com.example.BookStore.repository.bookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class bookService {

    @Autowired
    private bookRepository bookRepository;

    public List<Book> getBook(){
        return bookRepository.getBook();
    }

//    @Scheduled(cron = "0 0 0 * * SUN")
    @Scheduled(cron = "0 0 0 * * SUN")
    public void pullBook(){

        String allBook_url = "https://scb-test-book-publisher.herokuapp.com/books";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        //get all book
        ResponseEntity<Book[]> bookResponseEntity = restTemplate.exchange(
                allBook_url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Book[].class);

        List<Book> allBookList = new ArrayList<>();

        for(Book b : bookResponseEntity.getBody()){
            allBookList.add(b);
        }
        //save allBook in database
        bookRepository.saveBook(allBookList);

        String recommendBook_url = "https://scb-test-book-publisher.herokuapp.com/books/recommendation";
        //get recommend book
        ResponseEntity<Book[]> bookRecommend = restTemplate.exchange(
                recommendBook_url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Book[].class);

        List<Book> allRecommendBook = new ArrayList<>();

        for(Book b : bookRecommend.getBody()){
            allRecommendBook.add(b);
        }
        //save allRecommendBook in database
        bookRepository.saveBookRecommend(allRecommendBook);
    }
}
