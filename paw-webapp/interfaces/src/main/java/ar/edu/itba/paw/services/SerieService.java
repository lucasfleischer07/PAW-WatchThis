package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Serie;

import java.util.List;
import java.util.Optional;

public interface SerieService {
    List<Serie> getAllSeries();
    Optional<Serie> findByName(String name);
    Optional<Serie> findByGenre(String genre);
    Optional<Serie> findByDuration(String duration);
    Optional<Serie> findById(long id);
}
