package com.example.miniproject01.review.service;

import com.example.miniproject01.exception.NotFoundException;
import com.example.miniproject01.review.db.ReviewEntity;
import com.example.miniproject01.review.db.ReviewRepository;
import com.example.miniproject01.review.dto.ReviewDto;
import com.example.miniproject01.review.dto.ReviewRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 글쓰기
    public ReviewDto create(ReviewRequest reviewRequest) {

        ReviewEntity entity = ReviewEntity.builder()
                .movieTitle(reviewRequest.getMovieTitle())
                .content(reviewRequest.getContent())
                .createAt(LocalDateTime.now())
                .build()
                ;

        ReviewEntity savedEntity = reviewRepository.save(entity);

        return ReviewDto.builder()
                .id(savedEntity.getId())
                .movieTitle(savedEntity.getMovieTitle())
                .content(savedEntity.getContent())
                .createAt(savedEntity.getCreateAt())
                .modifiedAt(savedEntity.getModifiedAt())
                .build()
                ;
    }

    public List<ReviewDto> all() {

        return reviewRepository.findAll().stream()
                .map(entity -> ReviewDto.builder()
                        .id(entity.getId())
                        .movieTitle(entity.getMovieTitle())
                        .content(entity.getContent())
                        .createAt(entity.getCreateAt())
                        .modifiedAt(entity.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // 글 삭제
    public void reviewDelete(Long id) {

        if (!reviewRepository.existsById(id)) {
            throw new NotFoundException("Not Found");
        }
        reviewRepository.deleteById(id);
    }

    // 글 하나만 보기
    public ReviewDto reviewOnepick(Long id){

        ReviewEntity entity = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found"));

        return ReviewDto.builder()
                .id(entity.getId())
                .movieTitle(entity.getMovieTitle())
                .content(entity.getContent())
                .createAt(entity.getCreateAt())
                .modifiedAt(entity.getModifiedAt())
                .build()
                ;
    }

    // 글 수정
    @Transactional
    public ReviewDto reviewUpdate(Long id, ReviewRequest newReview) {

        ReviewEntity oldReview = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found"));

        oldReview.setMovieTitle(newReview.getMovieTitle());
        oldReview.setContent(newReview.getContent());
        oldReview.setModifiedAt(LocalDateTime.now());

        ReviewEntity updatedEntity = reviewRepository.save(oldReview);

        return ReviewDto.builder()
                .id(updatedEntity.getId())
                .movieTitle(updatedEntity.getMovieTitle())
                .content(updatedEntity.getContent())
                .createAt(updatedEntity.getCreateAt())
                .modifiedAt(updatedEntity.getModifiedAt())
                .build()
                ;
    }

}
