package com.example.miniproject01.genre.controller;


import com.example.miniproject01.genre.db.GenreEntity;
import com.example.miniproject01.genre.dto.GenreRequest;
import com.example.miniproject01.genre.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreApiController {

    private final GenreService genreService;

    @PostMapping("/create")
    public GenreEntity create(

            @Valid
            @RequestBody
            GenreRequest genreRequest

    ){
        return genreService.create(genreRequest);
    }

    @GetMapping("/list")
    public List<GenreEntity> list(

    ){
        return genreService.all();
    }

    @GetMapping("/delete/{id}")
    public String genreDelete(@PathVariable("id") Long id) {

        genreService.genreDelete(id);

        return "";
    }

}
