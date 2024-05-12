package com.example.miniproject01.review.service;


import com.example.miniproject01.genre.db.GenreEntity;
import com.example.miniproject01.genre.db.GenreRepository;
import com.example.miniproject01.genre.dto.GenreRequest;
import com.example.miniproject01.movie.db.MovieEntity;
import com.example.miniproject01.review.db.ReviewEntity;
import com.example.miniproject01.review.db.ReviewRepository;
import com.example.miniproject01.review.dto.ReviewRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public void reviewDelete(Long id) {

        reviewRepository.deleteById(id);
    }

    public ReviewEntity reviewOnepick(Long id){

        Optional<ReviewEntity> pickReview = reviewRepository.findById(id);

        return pickReview.orElse(null);
    }

    @Transactional
    public ReviewEntity reviewUpdate(Long id, ReviewEntity newReview) {

        ReviewEntity oldReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id " + id));

        oldReview.setMovieTitle(newReview.getMovieTitle());
        oldReview.setContent(newReview.getContent());
        oldReview.setModifiedAt(LocalDateTime.now());

        return reviewRepository.save(oldReview);
    }

}
