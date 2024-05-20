package com.example.miniproject01.genre.service;


import com.example.miniproject01.genre.db.GenreEntity;
import com.example.miniproject01.genre.db.GenreRepository;
import com.example.miniproject01.genre.dto.GenreDto;
import com.example.miniproject01.genre.dto.GenreRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final WebClient webClient = WebClient.create();

    // 글쓰기
//    public GenreEntity create(
//        GenreRequest genreRequest
//    ) {
//        var entity = GenreEntity.builder()
//                .id(genreRequest.getId())
//                .name(genreRequest.getName())
//                .build()
//                ;
//
//        return genreRepository.save(entity);
//    }

    public GenreDto create(GenreRequest genreRequest) {
        GenreEntity entity = GenreEntity.builder()
                .id(genreRequest.getId())
                .name(genreRequest.getName())
                .build()
                ;

        GenreEntity savedEntity = genreRepository.save(entity);
        return GenreDto.builder()
                .id(savedEntity.getId())
                .name(savedEntity.getName())
                .build()
                ;

    }

    // 리스트
//    public List<GenreEntity> all() {
//
//        return genreRepository.findAll();
//    }
    public List<GenreDto> all() {
        return genreRepository.findAll().stream()
                .map(entity -> GenreDto.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .build())
                .collect(Collectors.toList());
    }


    // 글 삭제
    public void genreDelete(Long id) {

        genreRepository.deleteById(id);
    }

    // 장르 API 저장
    public void genreApiSave() {
        String baseUrl = "https://api.themoviedb.org/3/genre/movie/list";
        String apiKey = "a986250901395deffed1ae6e646ae735";
        String language = "en-US";

        fetchAndSaveGenres(baseUrl, apiKey, language);
    }

    // 장르 API 불러오는 로직

    public void fetchAndSaveGenres(String baseUrl, String apiKey, String language) {

        try {
            URL url = new URL(String.format("%s?api_key=%s&language=%s", baseUrl, apiKey, language));

            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bf.readLine()) != null) {
                result.append(line);
            }
            bf.close();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result.toString());
            JSONArray genres = (JSONArray) jsonObject.get("genres");

            for (Iterator<?> iterator = genres.iterator(); iterator.hasNext(); ) {
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

    public void genreApiSave2() {
        String baseUrl = "https://api.themoviedb.org/3/genre/movie/list";
        String apiKey = "a986250901395deffed1ae6e646ae735";
        String language = "en-US";

        fetchAndSaveGenres(baseUrl, apiKey, language);
    }

    public void fetchAndSaveGenres2(String baseUrl, String apiKey, String language) {

        String url = String.format("%s?api_key=%s&language=%s", baseUrl, apiKey, language);

        webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        JSONParser jsonParser = new JSONParser();
                        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
                        JSONArray genres = (JSONArray) jsonObject.get("genres");

                        for (Object obj : genres) {
                            JSONObject genreObject = (JSONObject) obj;
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
                        return Mono.empty();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Error occurred while fetching data");
                        return Mono.error(e);
                    }
                }).subscribe();

    }

}
