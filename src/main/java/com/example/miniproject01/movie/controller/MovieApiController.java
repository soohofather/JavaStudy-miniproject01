package com.example.miniproject01.movie.controller;

import com.example.miniproject01.movie.db.MovieEntity;
import com.example.miniproject01.movie.db.MovieRepository;
import com.example.miniproject01.movie.dto.MovieSearch;
import com.example.miniproject01.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieApiController {

    private final MovieService movieService;
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/list")
    public List<MovieEntity> list(

    ){
        return movieService.all();
    }

    @GetMapping("/search")
    public List<MovieSearch> searchMovies(@RequestParam String title) {

        return movieService.searchMovies(title);
    }

    @GetMapping("/onepick/{movieId}")
    public MovieEntity movieOnepick(@PathVariable Long movieId){

        return movieService.movieOnepick(movieId);
    }


    @GetMapping("/popularapi")
    public void movieApiSave() {  // 반환 타입을 String에서 void로 변경하여 직접 출력

        String baseUrl = "https://api.themoviedb.org/3/movie/popular";
        String apiKey = "a986250901395deffed1ae6e646ae735";
        String language = "en-US";

        for(int page = 1; page <= 100; page++) {

            try {
                URL url = new URL(String.format("%s?api_key=%s&language=%s&page=%d", baseUrl, apiKey, language, page));
                HttpURLConnection pageUrl = (HttpURLConnection) url.openConnection();

                BufferedReader bf = new BufferedReader(new InputStreamReader(pageUrl.getInputStream(), "UTF-8"));
                String result = bf.readLine();
                bf.close();

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                JSONArray results = (JSONArray) jsonObject.get("results");

                for (Object obj : results) {
                    JSONObject movies = (JSONObject) obj;
                    Long movieId = (Long) movies.get("id");

                    // Check if genre already exists
                    if (!movieRepository.existsById(movieId)) {

                        JSONArray genreIds = (JSONArray) movies.get("genre_ids");
                        String originalTitle = (String) movies.get("original_title");
                        String title = (String) movies.get("title");
                        String releaseDate = (String) movies.get("release_date");
                        String posterPath = (String) movies.get("poster_path");
                        String overview = (String) movies.get("overview");

                        MovieEntity movie = MovieEntity.builder()
                                .movieId(movieId)
                                .genreId(genreIds.toString())
                                .originalTitle(originalTitle)
                                .title(title)
                                .releaseDate(releaseDate)
                                .posterPath(posterPath)
                                .overview(overview)
                                .build();

                        movieRepository.save(movie);
                        System.out.println("Saved new genre: ID " + movieId + ", Title " + title);

                    } else {
                        System.out.println("Popular already exists: ID " + movieId);
                    }
                }
                System.out.println("Popular updated successfully.");

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error occurred while fetching data");
            }
        }

    }

    @GetMapping("/nowplayapi")
    public void nowplayApiSave() {  // 반환 타입을 String에서 void로 변경하여 직접 출력

        String baseUrl = "https://api.themoviedb.org/3/movie/now_playing";
        String apiKey = "a986250901395deffed1ae6e646ae735";
        String language = "en-US";

        for(int page = 1; page <= 100; page++) {

            try {
                URL url = new URL(String.format("%s?api_key=%s&language=%s&page=%d", baseUrl, apiKey, language, page));
                HttpURLConnection pageUrl = (HttpURLConnection) url.openConnection();

                BufferedReader bf = new BufferedReader(new InputStreamReader(pageUrl.getInputStream(), "UTF-8"));
                String result = bf.readLine();
                bf.close();

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                JSONArray results = (JSONArray) jsonObject.get("results");

                for (Object obj : results) {
                    JSONObject movies = (JSONObject) obj;
                    Long movieId = (Long) movies.get("id");

                    // Check if genre already exists
                    if (!movieRepository.existsById(movieId)) {

                        JSONArray genreIds = (JSONArray) movies.get("genre_ids");
                        String originalTitle = (String) movies.get("original_title");
                        String title = (String) movies.get("title");
                        String releaseDate = (String) movies.get("release_date");
                        String posterPath = (String) movies.get("poster_path");
                        String overview = (String) movies.get("overview");

                        MovieEntity movie = MovieEntity.builder()
                                .movieId(movieId)
                                .genreId(genreIds.toString())
                                .originalTitle(originalTitle)
                                .title(title)
                                .releaseDate(releaseDate)
                                .posterPath(posterPath)
                                .overview(overview)
                                .build();

                        movieRepository.save(movie);
                        System.out.println("Saved new genre: ID " + movieId + ", Title " + title);

                    } else {
                        System.out.println("Now Playing already exists: ID " + movieId);
                    }
                }
                System.out.println("Now Playing updated successfully.");

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error occurred while fetching data");
            }
        }

    }

}
