package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbreakers.geektext.model.Rating;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class BookRatingAndCommentingController
{
    private final RatingService ratingService;

    @Autowired
    public BookRatingAndCommentingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public List<Rating> getRatings() {
        return ratingService.getRatings();
    }

    /*
    TODO:
    - Create rating for a book by a user on a 5-star scale with a datestamp
    - Create comment for a book by a user with a datestamp
    - Retrieve a list of all comments for a particular book
    - Retrieve average rating for a book
     */
}
