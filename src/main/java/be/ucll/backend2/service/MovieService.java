package be.ucll.backend2.service;

import be.ucll.backend2.controller.dto.MovieDto;
import be.ucll.backend2.model.Actor;
import be.ucll.backend2.model.Movie;
import be.ucll.backend2.repository.ActorRepository;
import be.ucll.backend2.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    public MovieService(MovieRepository movieRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesBetween(Optional<Integer> startYear, Optional<Integer> endYear) {
        if (startYear.isPresent() && endYear.isPresent()) {
            return movieRepository.findByYearBetween(startYear.get(), endYear.get());
        } else if (startYear.isPresent()) {
            return movieRepository.findByYearAfter(startYear.get());
        } else if (endYear.isPresent()) {
            return movieRepository.findByYearBefore(endYear.get());
        } else {
            return movieRepository.findAll();
        }
    }

    public Movie addMovie(MovieDto movieDto) {
        var movie = new Movie(
                movieDto.title(),
                movieDto.director(),
                movieDto.year()
        );
        for (var name : movieDto.actors()) {
            var actor = actorRepository.findByName(name).orElse(actorRepository.save(new Actor(name)));
            movie.addActor(actor);
        }
        return movieRepository.save(movie);
    }
}
