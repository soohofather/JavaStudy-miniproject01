package com.example.miniproject01.movie.controller;

import com.example.miniproject01.movie.db.MovieEntity;
import com.example.miniproject01.movie.db.MovieRepository;
import com.example.miniproject01.movie.dto.MovieDto;
import com.example.miniproject01.movie.dto.MovieOnePickDto;
import com.example.miniproject01.movie.dto.MovieSearch;
import com.example.miniproject01.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieApiController {

    private final MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/list")
    public List<MovieDto> list(

    ){
        return movieService.all();
    }

    @GetMapping("/search")
    public List<MovieSearch> searchMovies(@RequestParam String title) {

        return movieService.searchMovies(title);
    }

    @GetMapping("/onepick/{movieId}")
    public MovieOnePickDto movieOnepick(@PathVariable Long movieId){

        return movieService.movieOnepick(movieId);
    }


    @GetMapping("/popularapi")
    public void movieApiSave() {  // 반환 타입을 String에서 void로 변경하여 직접 출력

        movieService.movieApiSave();
    }

    @GetMapping("/nowplayapi")
    public void nowplayApiSave() {  // 반환 타입을 String에서 void로 변경하여 직접 출력

        movieService.nowplayApiSave();
    }

    @GetMapping("/popularapi2")
    public void movieApiSave2() {  // 반환 타입을 String에서 void로 변경하여 직접 출력

        movieService.movieApiSave2();
    }

    @GetMapping("/nowplayapi2")
    public void nowplayApiSave2() {  // 반환 타입을 String에서 void로 변경하여 직접 출력

        movieService.nowplayApiSave2();
    }

}
