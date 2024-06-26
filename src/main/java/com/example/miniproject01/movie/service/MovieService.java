package com.example.miniproject01.movie.service;

import com.example.miniproject01.exception.NotFoundException;
import com.example.miniproject01.genre.db.GenreEntity;
import com.example.miniproject01.genre.db.GenreRepository;
import com.example.miniproject01.genre.dto.GenreDto;
import com.example.miniproject01.movie.db.MovieEntity;
import com.example.miniproject01.movie.db.MovieRepository;
import com.example.miniproject01.movie.dto.MovieDto;
import com.example.miniproject01.movie.dto.MovieOnePickDto;
import com.example.miniproject01.movie.dto.MovieSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final WebClient webClient = WebClient.create();

    // 리스트
    public List<MovieDto> all() {

        List<MovieEntity> entities = movieRepository.findAll();
        List<MovieDto> movieDtos = new ArrayList<>();

        for (MovieEntity entity : entities) {
            MovieDto movieDto = MovieDto.builder()
                    .movieId(entity.getMovieId())
                    .genreId(entity.getGenreId())
                    .originalTitle(entity.getOriginalTitle())
                    .title(entity.getTitle())
                    .releaseDate(entity.getReleaseDate())
                    .posterPath(entity.getPosterPath())
                    .overview(entity.getOverview())
                    .build()
                    ;
            movieDtos.add(movieDto);
        }
        return movieDtos;
    }

    // 글 삭제
    public void movieDelete(Long id) {

        if(!movieRepository.existsByMovieId(id)) {
            throw new NotFoundException("Not found");
        }
        movieRepository.deleteById(id);
    }

    // 검색된 리스트
    public List<MovieSearch> searchMovies(String title) {
        if(movieRepository.findByTitleContaining(title) == null) {
            throw new NotFoundException("Not found");
        }
        return movieRepository.findByTitleContaining(title);
    }

    // 글 하나 보기
    public MovieOnePickDto movieOnepick(Long movieId){

        MovieEntity entity = movieRepository.findByMovieId(movieId).orElseThrow(() -> new NotFoundException("Not Found"));

        String genreIdString = entity.getGenreId();
        String cleaned = genreIdString.replace("[","").replace("]","").trim();
        String[] parts = cleaned.split(",");

        List<Long> genreIds = new ArrayList<>();
        for (String part : parts) {
            genreIds.add(Long.parseLong(part.trim()));
        }

        List<String> genreDtos = new ArrayList<>();
        for (Long genreId : genreIds) {
            GenreEntity genreEntity = genreRepository.findById(genreId)
                    .orElseThrow(() -> new NotFoundException("Not Found Genre"));
            genreDtos.add(genreEntity.getName());
        }

        return MovieOnePickDto.builder()
                .movieId(entity.getMovieId())
                .genres(genreDtos)
                .originalTitle(entity.getOriginalTitle())
                .title(entity.getTitle())
                .releaseDate(entity.getReleaseDate())
                .posterPath(entity.getPosterPath())
                .overview(entity.getOverview())
                .build()
                ;
    }

    // Popular Movies API Save
    public void movieApiSave() {
        String baseUrl = "https://api.themoviedb.org/3/movie/popular";
        String apiKey = "a986250901395deffed1ae6e646ae735";
        String language = "en-US";

        fetchAndSaveMovies(baseUrl, apiKey, language);
    }

    // Now Playing Movies API Save
    public void nowplayApiSave() {
        String baseUrl = "https://api.themoviedb.org/3/movie/now_playing";
        String apiKey = "a986250901395deffed1ae6e646ae735";
        String language = "en-US";

        fetchAndSaveMovies(baseUrl, apiKey, language);
    }

    // Fetch and Save Movies
    private void fetchAndSaveMovies(String baseUrl, String apiKey, String language) {
        for (int page = 1; page <= 100; page++) {
            try {
                URL url = new URL(String.format("%s?api_key=%s&language=%s&page=%d", baseUrl, apiKey, language, page));
                HttpURLConnection pageUrl = (HttpURLConnection) url.openConnection();

                BufferedReader bf = new BufferedReader(new InputStreamReader(pageUrl.getInputStream(), "UTF-8"));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bf.readLine()) != null) {
                    result.append(line);
                }
                bf.close();


                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result.toString());
                JSONArray results = (JSONArray) jsonObject.get("results");

                for (Object obj : results) {
                    JSONObject movies = (JSONObject) obj;
                    Long movieId = (Long) movies.get("id");

                    if (!movieRepository.existsByMovieId(movieId)) {
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
                    } else {
                        System.out.println("Popular already exists: ID " + movieId);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error occurred while fetching data for page " + page);
            }
            System.out.println("Movies updated successfully");
        }
    }

    // Popular Movies API Save
    public void movieApiSave2() {
        String baseUrl = "https://api.themoviedb.org/3/movie/popular";
        String apiKey = "a986250901395deffed1ae6e646ae735";
        String language = "en-US";

        fetchAndSaveMovies2(baseUrl, apiKey, language);
    }

    // Now Playing Movies API Save
    public void nowplayApiSave2() {
        String baseUrl = "https://api.themoviedb.org/3/movie/now_playing";
        String apiKey = "a986250901395deffed1ae6e646ae735";
        String language = "en-US";

        fetchAndSaveMovies2(baseUrl, apiKey, language);
    }

    // Fetch and Save Movies
    private void fetchAndSaveMovies2(String baseUrl, String apiKey, String language) {
        for (int page = 1; page <= 100; page++) {
            final int currentPage = page;

            String url = String.format("%s?api_key=%s&language=%s&page=%d", baseUrl, apiKey, language, page);

            webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMap(response -> {
                        try {
                            JSONParser jsonParser = new JSONParser();
                            JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
                            JSONArray results = (JSONArray) jsonObject.get("results");

                            for (Object obj : results) {
                                JSONObject movies = (JSONObject) obj;
                                Long movieId = (Long) movies.get("id");

                                if (!movieRepository.existsByMovieId(movieId)) {
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
                                    System.out.println("Saved new movie: ID " + movieId + ", Title " + title);
                                } else {
                                    System.out.println("Movie already exists: ID " + movieId);
                                }
                            }
                            System.out.println("Movies updated successfully for page " + currentPage);
                            return Mono.empty();
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Error occurred while fetching data for page " + currentPage);
                            return Mono.error(e);
                        }
                    }).block();
        }
    }


}
