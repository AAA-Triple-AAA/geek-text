package com.springbreakers.geektext.service;
import com.springbreakers.geektext.model.BookSorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class BookSortingService {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookSortingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<BookSorting> getAllBooks() {
        String sql = "SELECT * FROM book";
        return jdbcTemplate.query(sql, BookSorting.BOOK_MAPPER);
    }
    public List<BookSorting> getBooksByGenre(int genre_id) {
        String sql = "SELECT * FROM book WHERE genre_id = ?";
        return jdbcTemplate.query(sql, BookSorting.BOOK_MAPPER, genre_id);
    }

    public List<BookSorting> getTopSellers() {
        String sql = "SELECT * FROM book ORDER BY copies_sold DESC LIMIT 10";
        return jdbcTemplate.query(sql, BookSorting.BOOK_MAPPER);
    }


}
