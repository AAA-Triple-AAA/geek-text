package com.springbreakers.geektext.model;
import org.springframework.jdbc.core.RowMapper;

public class BookSorting {
    private int id;
    private long isbn;
    private String title;
    private String description;
    private int year;
    private double price;
    private long copies_sold;
    private int genre_id;
    private int publisher_id;
    private int author_id;
    private double rating;

    public BookSorting(int id, long isbn, String title, String description, int year, double price, long copies_sold, int genre_id, int publisher_id, int author_id, double rating) {
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
        this.rating = rating;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getIsbn() {
        return isbn;
    }
    public void setIsbn(long isbn) {
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
    public long getCopies_sold() {
        return copies_sold;
    }
    public void setCopies_sold(long copies_sold) {
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
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public static final RowMapper<BookSorting> BOOK_MAPPER = (rs, rowNum) -> {
        return new BookSorting(rs.getInt("id"),
                rs.getLong("isbn"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getInt("year"),
                rs.getDouble("price"),
                rs.getLong("copies_sold"),
                rs.getInt("genre_id"),
                rs.getInt("publisher_id"),
                rs.getInt("author_id"),
                rs.getDouble("rating"));
    };
}
