package be.ucll.backend2.controller;

import be.ucll.backend2.controller.dto.MovieDto;
import be.ucll.backend2.model.Movie;
import be.ucll.backend2.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<MovieDto> getMovies(@RequestParam Optional<Integer> startYear,
                                    @RequestParam Optional<Integer> endYear) {
        List<Movie> moviesFromDb;
        if (startYear.isPresent() || endYear.isPresent()) {
            moviesFromDb = movieService.getMoviesBetween(startYear, endYear);
        } else {
            moviesFromDb = movieService.getAllMovies();
        }
        return moviesFromDb.stream().map(
                movie -> MovieDto.fromMovie(movie)
        ).toList();
    }

    @PostMapping
    public Movie addMovie(@RequestBody MovieDto movieDto) {
        return movieService.addMovie(movieDto);
    }
}
