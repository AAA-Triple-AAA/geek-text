package com.springbreakers.geektext.model;

import org.springframework.jdbc.core.RowMapper;

public class Author {
    private int id;
    private String first_name;
    private String last_name;
    private String biography;
    private int publisher_id;

    public Author(int id, String first_name, String last_name, String biography, int publisher_id) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.biography = biography;
        this.publisher_id = publisher_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
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

    public int getPublisher_id() {
        return publisher_id;
    }

    public static final RowMapper<Author> AUTHOR_MAPPER = (rs, rowNum) -> {
        return new Author(rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("biography"),
                rs.getInt("publisher_id"));
    };

}
