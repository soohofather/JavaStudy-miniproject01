package com.example.miniproject01.review.service;


import com.example.miniproject01.genre.db.GenreEntity;
import com.example.miniproject01.genre.db.GenreRepository;
import com.example.miniproject01.genre.dto.GenreRequest;
import com.example.miniproject01.review.db.ReviewEntity;
import com.example.miniproject01.review.db.ReviewRepository;
import com.example.miniproject01.review.dto.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 글쓰기
    public ReviewEntity create(
            ReviewRequest reviewRequest
    ) {
        var entity = ReviewEntity.builder()
                .movieTitle(reviewRequest.getMovieTitle())
                .content(reviewRequest.getContent())
                .createAt(LocalDateTime.now())
                .build()
                ;

        return reviewRepository.save(entity);
    }

    public List<ReviewEntity> all() {

        return reviewRepository.findAll();
    }
}
