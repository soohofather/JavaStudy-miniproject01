package com.example.miniproject01.review.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewDto {

    private Long id;
    private String movieTitle;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
