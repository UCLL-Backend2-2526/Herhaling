package be.ucll.backend2.controller.dto;

import be.ucll.backend2.model.Movie;

import java.util.List;

public record MovieDto(
        Long id,
        String title,
        String director,
        int year,
        List<String> actors
) {
    public MovieDto(Movie movie) {
        this(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getYear(),
                movie.getActors().stream().map(actor -> actor.getName()).toList()
        );
    }
}
