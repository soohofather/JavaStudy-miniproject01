package com.example.miniproject01.genre.controller;

import com.example.miniproject01.genre.db.GenreEntity;
import com.example.miniproject01.genre.db.GenreRepository;
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

    @GetMapping("/api")
    public void genreApiSave() {  // 반환 타입을 String에서 void로 변경하여 직접 출력

        try {
            URL url = new URL("https://api.themoviedb.org/3/genre/movie/list?api_key=a986250901395deffed1ae6e646ae735&language=en-US");
            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONArray genres = (JSONArray) jsonObject.get("genres");

            for (Iterator<?> iterator = genres.iterator(); iterator.hasNext();) {
                JSONObject genreObject = (JSONObject) iterator.next();
                Long id = (Long) genreObject.get("id");
                String name = (String) genreObject.get("name");

                // Check if genre already exists
                if (!genreRepository.existsById(id)) {
                    GenreEntity genre = new GenreEntity(id, name);
                    genreRepository.save(genre);
                    System.out.println("Saved new genre: ID " + id + ", Name " + name);
                } else {
                    System.out.println("Genre already exists: ID " + id + ", Name " + name);
                }
            }
            System.out.println("Genres updated successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while fetching data");
        }
    }
}
