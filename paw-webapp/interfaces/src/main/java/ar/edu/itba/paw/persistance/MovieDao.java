package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Movie;

import java.util.Optional;

public interface MovieDao {
    Optional<Movie> findByName(String name);
    Optional<Movie> findByGenre(String genre);
    Optional<Movie> findByDuration(String duration);
}
