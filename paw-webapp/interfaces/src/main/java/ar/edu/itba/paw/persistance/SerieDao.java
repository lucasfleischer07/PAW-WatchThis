package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Movie;
import ar.edu.itba.paw.models.Serie;

import java.util.List;
import java.util.Optional;

public interface SerieDao {
    List<Serie> getAllSeries();
    Optional<Serie> findByName(String name);
    List<Serie> findByGenre(String genre);
    List<Serie> findByDuration(int durationFrom, int durationTo);
    List<Serie> findByDurationAndGenre(String genre, int durationFrom, int durationTo);
    Optional<Serie> findById(long id);

}
