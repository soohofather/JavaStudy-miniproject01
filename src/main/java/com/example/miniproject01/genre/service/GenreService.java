package com.example.miniproject01.genre.service;


import com.example.miniproject01.genre.db.GenreEntity;
import com.example.miniproject01.genre.db.GenreRepository;
import com.example.miniproject01.genre.dto.GenreRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    // 글쓰기
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

    // 리스트
    public List<GenreEntity> all() {
        return genreRepository.findAll();
    }

    // 글 삭제
    public void genreDelete(Long id) {

        genreRepository.deleteById(id);
    }


}
