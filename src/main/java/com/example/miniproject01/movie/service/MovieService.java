package com.example.miniproject01.movie.service;

import com.example.miniproject01.genre.db.GenreEntity;
import com.example.miniproject01.genre.db.GenreRepository;
import com.example.miniproject01.genre.dto.GenreRequest;
import com.example.miniproject01.movie.db.MovieEntity;
import com.example.miniproject01.movie.db.MovieRepository;
import com.example.miniproject01.movie.dto.MovieRequest;
import com.example.miniproject01.movie.dto.MovieSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    // 글쓰기
    public MovieEntity create(
            MovieRequest movieRequest
    ) {
        var entity = MovieEntity.builder()
                .movieId(movieRequest.getMovieId())
                .genreId(movieRequest.getGenreId())
                .originalTitle(movieRequest.getOriginalTitle())
                .title(movieRequest.getTitle())
                .releaseDate(movieRequest.getReleaseDate())
                .posterPath(movieRequest.getPosterPath())
                .overview(movieRequest.getOverview())
                .build()
                ;

        return movieRepository.save(entity);
    }

    // 리스트
    public List<MovieEntity> all() {

        return movieRepository.findAll();
    }

    // 글 삭제
    public void movieDelete(Long id) {

        movieRepository.deleteById(id);
    }

    // 검색된 리스트
    public List<MovieSearch> searchMovies(String title) {

        return movieRepository.findByTitleContaining(title);
    }




}
