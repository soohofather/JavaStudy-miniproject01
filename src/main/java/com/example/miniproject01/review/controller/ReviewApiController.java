package com.example.miniproject01.review.controller;


import com.example.miniproject01.genre.db.GenreEntity;
import com.example.miniproject01.genre.dto.GenreRequest;
import com.example.miniproject01.review.db.ReviewEntity;
import com.example.miniproject01.review.dto.ReviewRequest;
import com.example.miniproject01.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping("/create")
    public ReviewEntity create(

            @Valid
            @RequestBody
            ReviewRequest reviewRequestRequest

    ){
        return reviewService.create(reviewRequestRequest);
    }
}
