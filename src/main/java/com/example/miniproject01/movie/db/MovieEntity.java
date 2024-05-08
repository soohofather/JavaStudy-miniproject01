package com.example.miniproject01.movie.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name = "movie")
public class MovieEntity {

    @Id
    private Long id;
    private Long genreId;
    private String originalTitle;
    private String title;
    private String releaseDate;
    private String posterPath;
    private String overview;
}
