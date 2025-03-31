package com.springbreakers.geektext.service;
import com.springbreakers.geektext.model.BookSorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

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
        try {
            String sql = "SELECT * FROM book WHERE genre_id = ?";
            return jdbcTemplate.query(sql, BookSorting.BOOK_MAPPER, genre_id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error while retrieving books by genre", e);
        }
    }

    public List<BookSorting> getTopSellers() {
        try {
            String sql = "SELECT * FROM book ORDER BY copies_sold DESC LIMIT 10";
            return jdbcTemplate.query(sql, BookSorting.BOOK_MAPPER);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error while retrieving top sellers", e);
        }
    }

    public List<BookSorting> getBooksByRating(double rating) {
        try {
            String sql = "SELECT b.id, b.isbn, b.description, b.year, b.price, b.copies_sold, " +
                    "b.publisher_id, b.author_id, b.title, b.genre_id, AVG(r.rating) AS rating " +
                    "FROM book AS b " +
                    "JOIN rating AS r ON b.id = r.book_id " +
                    "GROUP BY b.id " +
                    "HAVING AVG(r.rating) >= ?;";
            return jdbcTemplate.query(sql, BookSorting.BOOK_MAPPER, rating);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error while retrieving books by rating", e);
        }
    }

    public void discountBooksByPublisher(int publisher_id, Double discount) {
        try {
            Map<Integer, Double> discountMap = Map.of(
                    1, 10.50, 2, 12.00, 3, 8.75, 4, 15.20,
                    5, 9.90, 6, 11.50, 7, 13.25, 8, 9.00,
                    9, 14.75, 10, 10.00
            );

            Double discountPercent = (discount != null) ? discount : discountMap.get(publisher_id);

            if (discountPercent == null) {
                throw new IllegalArgumentException("Invalid publisher ID: " + publisher_id);
            }

            if (discountPercent < 0 || discountPercent > 100) {
                throw new IllegalArgumentException("Discount percentage must be between 0% and 100%");
            }

            double discountFactor = discountPercent / 100;

            String sql = "UPDATE book SET price = price * (1 - ?) WHERE publisher_id = ?";
            jdbcTemplate.update(sql, discountFactor, publisher_id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error while applying discount for publisher ID: " + publisher_id, e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid input: " + e.getMessage(), e);
        }
    }

}
