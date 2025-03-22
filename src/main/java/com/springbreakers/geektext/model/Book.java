package com.springbreakers.geektext.model;

import org.springframework.jdbc.core.RowMapper;

public class Book {
    private int id;
    private String isbn;
    private String title;
    private String description;
    private int year;
    private double price;
    private int copiesSold;
    private int genreId;
    private int publisherId;
    private int authorId;

    public Book() {}

    public Book(int id, String isbn, String title, String description, int year, double price, int copiesSold, int genreId, int publisherId, int authorId) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.year = year;
        this.price = price;
        this.copiesSold = copiesSold;
        this.genreId = genreId;
        this.publisherId = publisherId;
        this.authorId = authorId;
    }

    public Book(String isbn, String title, String description, int year, double price, int copiesSold, int genreId, int publisherId, int authorId) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.year = year;
        this.price = price;
        this.copiesSold = copiesSold;
        this.genreId = genreId;
        this.publisherId = publisherId;
        this.authorId = authorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCopiesSold() {
        return copiesSold;
    }

    public void setCopiesSold(int copiesSold) {
        this.copiesSold = copiesSold;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public static final RowMapper<Book> BOOK_MAPPER = (rs, rowNum) -> {
        return new Book(rs.getInt("id"),
                rs.getString("isbn"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getInt("year"),
                rs.getDouble("price"),
                rs.getInt("copies_sold"),
                rs.getInt("genre_id"),
                rs.getInt("publisher_id"),
                rs.getInt("author_id"));
    };
}
