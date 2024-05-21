package com.example.miniproject01.movie.dto;

import com.example.miniproject01.genre.dto.GenreDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MovieOnePickDto {

    private Long movieId;
    private List<String> genres;
    private String originalTitle;
    private String title;
    private String releaseDate;
    private String posterPath;
    private String overview;
}
