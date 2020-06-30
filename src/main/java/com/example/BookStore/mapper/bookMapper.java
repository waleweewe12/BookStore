package com.example.BookStore.mapper;

import com.example.BookStore.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class bookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();
        book.setAuthor_name(resultSet.getString("author_name"));
        book.setBook_name(resultSet.getString("book_name"));
        book.setId(resultSet.getInt("id"));
        book.setPrice(resultSet.getDouble("price"));
        return book;
    }
}
