package com.example.miniproject01.genre.service;


import com.example.miniproject01.genre.db.GenreEntity;
import com.example.miniproject01.genre.db.GenreRepository;
import com.example.miniproject01.genre.dto.GenreRequest;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
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


}
