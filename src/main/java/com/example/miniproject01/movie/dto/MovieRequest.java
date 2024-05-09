package com.example.miniproject01.movie.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MovieRequest {

    @NotBlank
    private Long movieId;
    @NotBlank
    private String genreId;
    @NotBlank
    private String originalTitle;
    @NotBlank
    private String title;
    @NotBlank
    private String releaseDate;
    private String posterPath;
    @NotBlank
    private String overview;
}
