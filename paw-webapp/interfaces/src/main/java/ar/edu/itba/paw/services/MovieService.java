package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> getAllMovies();
    Optional<Movie> findByName(String name);
    Optional<Movie> findByGenre(String genre);
    Optional<Movie> findByDuration(String duration);
    Optional<Movie> findById(long id);

}
