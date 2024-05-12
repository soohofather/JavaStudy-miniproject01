package com.example.miniproject01.review.controller;


import com.example.miniproject01.genre.db.GenreEntity;
import com.example.miniproject01.genre.dto.GenreRequest;
import com.example.miniproject01.movie.db.MovieEntity;
import com.example.miniproject01.review.db.ReviewEntity;
import com.example.miniproject01.review.dto.ReviewRequest;
import com.example.miniproject01.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    public List<ReviewEntity> list(

    ){
        return reviewService.all();
    }

    @GetMapping("/delete/{id}")
    public String reviewDelete(@PathVariable Long id) {

        reviewService.reviewDelete(id);

        return "";
    }

    @GetMapping("/onepick/{id}")
    public ReviewEntity reviewOnepick(@PathVariable("id") Long id){

        return reviewService.reviewOnepick(id);
    }

}
