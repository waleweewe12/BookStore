package com.example.BookStore.mapper;

import com.example.BookStore.model.BookRecommend;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class bookRecommendMapper implements RowMapper<BookRecommend> {
    @Override
    public BookRecommend mapRow(ResultSet resultSet, int i) throws SQLException {
        BookRecommend bookRecommend = new BookRecommend();
        bookRecommend.setId(resultSet.getInt("id"));
        return bookRecommend;
    }
}
