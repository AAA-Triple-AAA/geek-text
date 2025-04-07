package com.springbreakers.geektext.service;

import com.springbreakers.geektext.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class AuthorService {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = Logger.getLogger(AuthorService.class.getName());

    @Autowired
    public AuthorService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Service method that retrieves all authors from the database.
     *
     * @return List of all authors.
     */
    public List<Author> getAuthors() {
        String sql = "SELECT * FROM author";
        return jdbcTemplate.query(sql, Author.AUTHOR_MAPPER);
    }

    /**
     * Service method that creates a new author record in the database.
     * Checks for duplicates based on first name, last name, and publisher ID.
     *
     * @param author Author object containing the necessary information to insert.
     * @return ResponseEntity with success, conflict, or error message depending on the outcome.
     */
    public ResponseEntity<String> createAuthor(Author author) {
        String sql = "INSERT INTO author (first_name, last_name, biography, publisher_id) VALUES (?, ?, ?, ?)";
        String checkSql = "SELECT COUNT(*) FROM author WHERE first_name = ? AND last_name = ? AND publisher_id = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, author.getFirst_name(), author.getLast_name(), author.getPublisherId());

        //If the author exists, return a response
        if (count > 0) {
            LOGGER.warning("Attempted to add a duplicate author: " + author.getFirst_name() + " " + author.getLast_name() + " (Publisher ID: " + author.getPublisherId() + ")");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate author not allowed: An author with this name and publisher already exists.");
        }
        //Insert author if no duplicate found
        try{
            this.jdbcTemplate.update(sql, author.getFirst_name(), author.getLast_name(), author.getBiography(), author.getPublisherId());
            return ResponseEntity.status(HttpStatus.CREATED).body("Author was added successfully.");
        }catch (DataIntegrityViolationException e){ //Integrity/Foreign key violations
            LOGGER.warning("Failed to add an author: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(("Duplicate entry not allowed."));
        }catch (Exception e){
            LOGGER.severe("Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

}
