package com.example.miniproject01.movie.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
}
