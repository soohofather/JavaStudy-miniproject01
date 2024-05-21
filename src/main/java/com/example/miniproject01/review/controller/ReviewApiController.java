package com.example.miniproject01.review.controller;


import com.example.miniproject01.genre.db.GenreEntity;
import com.example.miniproject01.genre.dto.GenreRequest;
import com.example.miniproject01.movie.db.MovieEntity;
import com.example.miniproject01.review.db.ReviewEntity;
import com.example.miniproject01.review.db.ReviewRepository;
import com.example.miniproject01.review.dto.ReviewDto;
import com.example.miniproject01.review.dto.ReviewRequest;
import com.example.miniproject01.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping("/create")
    public ReviewDto create(

            @Valid
            @RequestBody
            ReviewRequest reviewRequestRequest

    ){
        return reviewService.create(reviewRequestRequest);
    }

    @GetMapping("/list")
    public List<ReviewDto> list(

    ){
        return reviewService.all();
    }

    @GetMapping("/delete/{id}")
    public String reviewDelete(@PathVariable Long id) {

        reviewService.reviewDelete(id);

        return "";
    }

    @GetMapping("/onepick/{id}")
    public ReviewDto reviewOnepick(@PathVariable("id") Long id){

        return reviewService.reviewOnepick(id);
    }

    @PutMapping("/update/{id}")
    public ReviewDto reviewUpdate(
            @PathVariable("id")
            Long id,
            @Valid
            @RequestBody
            ReviewRequest newReview
    ){

        return reviewService.reviewUpdate(id, newReview);

    }


}
