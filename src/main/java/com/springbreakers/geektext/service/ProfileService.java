package com.springbreakers.geektext.service;

import com.springbreakers.geektext.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.springbreakers.geektext.model.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProfileService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Profile> getProfiles() {
        String sql = "SELECT * FROM \"user\"";
        return jdbcTemplate.query(sql, Profile.Profile_MAPPER);
    }
}
