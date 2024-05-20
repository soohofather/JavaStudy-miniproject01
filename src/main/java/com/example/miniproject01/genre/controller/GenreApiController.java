package com.example.miniproject01.genre.controller;

import com.example.miniproject01.genre.db.GenreEntity;
import com.example.miniproject01.genre.db.GenreRepository;
import com.example.miniproject01.genre.dto.GenreDto;
import com.example.miniproject01.genre.dto.GenreRequest;
import com.example.miniproject01.genre.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Iterator;


@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreApiController {

    private final GenreService genreService;

    @Autowired
    private GenreRepository genreRepository;

    @PostMapping("/create")
    public GenreDto create(

            @Valid
            @RequestBody
            GenreRequest genreRequest

    ){
        return genreService.create(genreRequest);
    }

    @GetMapping("/list")
    public List<GenreDto> list(){

        return genreService.all();
    }

    @GetMapping("/delete/{id}")
    public String genreDelete(@PathVariable("id") Long id) {

        genreService.genreDelete(id);
        return "";
    }

    @GetMapping("/api")
    public void genreApiSave() {  // 반환 타입을 String에서 void로 변경하여 직접 출력

        genreService.genreApiSave();
    }

    @GetMapping("/api2")
    public void genreApiSave2() {  // 반환 타입을 String에서 void로 변경하여 직접 출력

        genreService.genreApiSave2();
    }
}
