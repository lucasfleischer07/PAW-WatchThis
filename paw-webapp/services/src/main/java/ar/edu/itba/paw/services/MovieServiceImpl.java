package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Movie;
import ar.edu.itba.paw.persistance.MovieDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieDao movieDao;

    @Autowired
    public MovieServiceImpl(final MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public Optional<Movie> findByName(String name) {
        return movieDao.findByName(name);
    }

    @Override
    public Optional<Movie> findByGenre(String genre) {
        return movieDao.findByGenre(genre);
    }

    @Override
    public Optional<Movie> findByDuration(String duration) {
        return movieDao.findByDuration(duration);
    }
}
