package com.example.miniproject01.movie.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MovieDto {

    private Long movieId;
    private String genreId;
    private String originalTitle;
    private String title;
    private String releaseDate;
    private String posterPath;
    private String overview;

}
