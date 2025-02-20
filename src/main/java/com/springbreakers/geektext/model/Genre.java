package com.springbreakers.geektext.model;

import org.springframework.jdbc.core.RowMapper;

public class Genre {
    private int id;
    private String name;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final RowMapper<Genre> GENRE_MAPPER = (rs, rowNum) -> {
        return new Genre(rs.getInt("id"), rs.getString("name"));
    };
}
