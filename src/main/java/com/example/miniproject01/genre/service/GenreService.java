package com.example.miniproject01.genre.service;


import com.example.miniproject01.genre.db.GenreEntity;
import com.example.miniproject01.genre.db.GenreRepository;
import com.example.miniproject01.genre.dto.GenreRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreEntity create(
        GenreRequest genreRequest
    ) {
        var entity = GenreEntity.builder()
                .id(genreRequest.getId())
                .name(genreRequest.getName())
                .build()
                ;

        return genreRepository.save(entity);
    }
}
