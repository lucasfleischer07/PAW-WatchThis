package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieDao {
    List<Movie> getAllMovies();
    Optional<Movie> findByName(String name);
    List<Movie> findByGenre(String genre);
    List<Movie> findByDuration(int durationFrom, int durationTo);
    List<Movie> findByDurationAndGenre(String genre,int durationFrom, int durationTo);
    Optional<Movie> findById(long id);
}
