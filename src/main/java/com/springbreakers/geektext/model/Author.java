package com.springbreakers.geektext.model;

import org.springframework.jdbc.core.RowMapper;

public class Author {
    private int id;
    private String first_name;
    private String last_name;
    private String biography;
    private int publisherId;

    public Author() {
    }

    public Author(int id, String first_name, String last_name, String biography, int publisherId) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.biography = biography;
        this.publisherId = publisherId;
    }

    public Author(String first_name, String last_name, String biography, int publisherId) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.biography = biography;
        this.publisherId = publisherId;
    }

    public int getId() {
        return id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getBiography() {
        return biography;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public static final RowMapper<Author> AUTHOR_MAPPER = (rs, rowNum) -> {
        return new Author(rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("biography"),
                rs.getInt("publisher_id"));
    };
}
