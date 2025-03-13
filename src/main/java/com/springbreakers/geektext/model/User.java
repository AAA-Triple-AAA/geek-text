package com.springbreakers.geektext.model;

import org.springframework.jdbc.core.RowMapper;

public class User {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String role;
    private String sessionApiKey;

    public User(){};

    public User(int id, String username, String password, String firstName, String lastName, String email, String address, String role, String sessionApiKey) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.role = role;
        this.sessionApiKey = sessionApiKey;
    }

    public User(String username, String password, String firstName, String lastName, String email, String address, String role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.role = role;
        this.sessionApiKey = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() { return role;}

    public void setRole(String role) { this.role = role;}

    public String getSessionApiKey() { return sessionApiKey;};

    public void setSessionApiKey(String sessionApiKey) {this.sessionApiKey = sessionApiKey;}

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", first_name=" + firstName + ", last_name=" + lastName + ", email=" + email + ", address=" + address + ", role=" + role + ", sessionApiKey=" + sessionApiKey + '}';
    }

    public static final RowMapper<User> USER_MAPPER = (rs, rowNum) -> {
        return new User(rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("address"),
                rs.getString("role"),
                rs.getString("session_api_key"));
    };
}
