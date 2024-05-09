package com.example.miniproject01.movie.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Query("SELECT m FROM movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<MovieEntity> findByTitleContaining(@Param("title") String title);
}
