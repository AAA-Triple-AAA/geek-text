package com.springbreakers.geektext.model;

import org.springframework.jdbc.core.RowMapper;

public class Book {
    private int id;
    private String isbn;
    private String title;
    private String description;
    private int year;
    private double price;
    private int copies_sold;
    private int genre_id;
    private int publisher_id;
    private int author_id;

    public Book(int id, String isbn, String title, String description, int year, double price, int copies_sold, int genre_id, int publisher_id, int author_id) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.year = year;
        this.price = price;
        this.copies_sold = copies_sold;
        this.genre_id = genre_id;
        this.publisher_id = publisher_id;
        this.author_id = author_id;
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

    public int getCopies_sold() {
        return copies_sold;
    }

    public void setCopies_sold(int copies_sold) {
        this.copies_sold = copies_sold;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public int getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
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
