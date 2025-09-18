package be.ucll.backend2.unit;

import be.ucll.backend2.model.Movie;
import be.ucll.backend2.repository.MovieRepository;
import be.ucll.backend2.service.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    public void givenThereAreMovies_whenGetMoviesWithoutRange_thenAllMoviesAreReturned() {
        // Given
        final var movieInDb = new Movie(
                "The Lord of the Rings: The Fellowship of the Ring",
                "Peter Jackson",
                2001
        );
        Mockito.when(movieRepository.findAll()).thenReturn(List.of(movieInDb));

        // When
        final var returnedMovies = movieService.getMoviesBetween(Optional.empty(), Optional.empty());

        // Then
        Assertions.assertNotNull(returnedMovies);
        Assertions.assertEquals(1, returnedMovies.size());
        Assertions.assertEquals(movieInDb, returnedMovies.getFirst());
        Mockito.verify(movieRepository).findAll();
    }

    @Test
    public void givenThereAreMoviesAfterStartYear_whenGetMoviesBetweenWithStartYear_thenMoviesAfterYearAreReturned() {
        // Given
        final var movieInDb = new Movie(
                "The Lord of the Rings: The Fellowship of the Ring",
                "Peter Jackson",
                2001
        );
        Mockito.when(movieRepository.findByYearAfter(2000)).thenReturn(List.of(movieInDb));

        // When
        final var returnedMovies = movieService.getMoviesBetween(Optional.of(2000), Optional.empty());

        // Then
        Assertions.assertNotNull(returnedMovies);
        Assertions.assertEquals(1, returnedMovies.size());
        Assertions.assertEquals(movieInDb, returnedMovies.getFirst());
        Mockito.verify(movieRepository).findByYearAfter(2000);
    }

    @Test
    public void givenThereAreMoviesBeforeEndYear_whenGetMoviesBetweenWithEndYear_thenMoviesBeforeYearAreReturned() {
        // Given
        final var movieInDb = new Movie(
                "The Lord of the Rings: The Fellowship of the Ring",
                "Peter Jackson",
                2001
        );
        Mockito.when(movieRepository.findByYearBefore(2010)).thenReturn(List.of(movieInDb));

        // When
        final var returnedMovies = movieService.getMoviesBetween(Optional.empty(), Optional.of(2010));

        // Then
        Assertions.assertNotNull(returnedMovies);
        Assertions.assertEquals(1, returnedMovies.size());
        Assertions.assertEquals(movieInDb, returnedMovies.getFirst());
        Mockito.verify(movieRepository).findByYearBefore(2010);
    }

    @Test
    public void givenThereAreMoviesAfterStartYearAndBeforeEndYear_whenGetMoviesBetweenWithStartAndEndYear_thenMoviesAfterStartYearAndBeforeEndYearAreReturned() {
        // Given
        final var movieInDb = new Movie(
                "The Lord of the Rings: The Fellowship of the Ring",
                "Peter Jackson",
                2001
        );
        Mockito.when(movieRepository.findByYearBetween(2000, 2010)).thenReturn(List.of(movieInDb));

        // When
        final var returnedMovies = movieService.getMoviesBetween(Optional.of(2000), Optional.of(2010));

        // Then
        Assertions.assertNotNull(returnedMovies);
        Assertions.assertEquals(1, returnedMovies.size());
        Assertions.assertEquals(movieInDb, returnedMovies.getFirst());
        Mockito.verify(movieRepository).findByYearBetween(2000, 2010);
    }
}
