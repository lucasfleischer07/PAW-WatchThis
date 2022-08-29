package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Serie;

import java.util.Optional;

public interface SerieService {
    Optional<Serie> findByName(String name);
    Optional<Serie> findByGenre(String genre);
    Optional<Serie> findByDuration(String duration);
}
