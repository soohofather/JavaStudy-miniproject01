package com.example.miniproject01.movie.db;

import com.example.miniproject01.movie.dto.MovieSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

//    @Query("SELECT m.movieId, m.title, m.releaseDate FROM movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    @Query("SELECT new com.example.miniproject01.movie.dto.MovieSearch(m.movieId, m.title, m.releaseDate) " + "FROM movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<MovieSearch> findByTitleContaining(@Param("title") String title);
}
