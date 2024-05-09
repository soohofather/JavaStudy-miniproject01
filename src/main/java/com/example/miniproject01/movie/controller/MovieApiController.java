package com.example.miniproject01.movie.controller;

import com.example.miniproject01.movie.db.MovieEntity;
import com.example.miniproject01.movie.db.MovieRepository;
import com.example.miniproject01.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

    @GetMapping("/popularapi")
    public void movieApiSave() {  // 반환 타입을 String에서 void로 변경하여 직접 출력

        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=a986250901395deffed1ae6e646ae735&language=en-US");
            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String result = bf.readLine();

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
                            .build()
                            ;

                    movieRepository.save(movie);
                    System.out.println("Saved new genre: ID " + movieId + ", Title " + title);

                } else {
                    System.out.println("Genre already exists: ID " + movieId);
                }
            }
            System.out.println("Genres updated successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while fetching data");
        }
    }

}
