package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Serie;

import java.util.Optional;

public interface SerieDao {
    Optional<Serie> findByName(String name);
    Optional<Serie> findByGenre(String genre);
    Optional<Serie> findByDuration(String duration);
}
