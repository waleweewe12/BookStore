package com.example.BookStore.repository;

import com.example.BookStore.mapper.bookMapper;
import com.example.BookStore.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository("book")
public class bookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Book> getBook(){

        String sql_book = "SELECT * FROM book WHERE id NOT IN (SELECT book.id FROM book INNER JOIN book_recommend WHERE book.id = book_recommend.id);";
        List<Book> allBookList = jdbcTemplate.query(sql_book,new bookMapper());

        String sql_bookRecommend = "SELECT * FROM book INNER JOIN book_recommend WHERE book.id = book_recommend.id;";
        List<Book> allRecommendBook = jdbcTemplate.query(sql_bookRecommend,new bookMapper());

        allRecommendBook.addAll(allBookList);

        return allRecommendBook;
    }

    public void saveBook(List<Book> allBookList){
        try {
            //delete old bookList
            String sql_deleteBook = "DELETE FROM book WHERE 1";
            jdbcTemplate.update(sql_deleteBook);

            //insert new bookList
            String sql_insertBook = "INSERT INTO book VALUES(?,?,?,?)";
            allBookList.forEach(item->
                    jdbcTemplate.update(sql_insertBook,
                            item.getId(),
                            item.getAuthor_name(),
                            item.getPrice(),
                            item.getBook_name()));
        }catch(DataAccessException ex){
            log.error("insert book failed");
        }
    }

    public void saveBookRecommend(List<Book> allRecommendBook){
        try{
            //delete old bookRecommend
            String sql_deleteBookRecommend = "DELETE FROM book_recommend WHERE 1";
            jdbcTemplate.update(sql_deleteBookRecommend);

            //insert new bookRecommend
            String sql_insertBookRecommend = "INSERT INTO book_recommend VALUES(?)";
            allRecommendBook.forEach(item->
                    jdbcTemplate.update(sql_insertBookRecommend, item.getId()));
        }catch(DataAccessException ex){
            log.error("save recommend_book failed");
        }
    }

}
