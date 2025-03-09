package com.springbreakers.geektext.model;

import org.springframework.jdbc.core.RowMapper;

public class Wishlist {
    private int id;
    private String name;
    private int userId;

    public Wishlist(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static final RowMapper<Wishlist> WISHLIST_MAPPER = (rs, rowNum) ->
            new Wishlist(rs.getInt("id"), rs.getString("name"), rs.getInt("user_id"));
}
