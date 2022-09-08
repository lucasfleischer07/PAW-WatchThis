package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Movie;
import ar.edu.itba.paw.persistance.MovieDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieDao movieDao;

    @Autowired
    public MovieServiceImpl(final MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieDao.getAllMovies();
    }

    @Override
    public Optional<Movie> findByName(String name) {
        return movieDao.findByName(name);
    }

    @Override
    public List<Movie> findByGenre(String genre) {
        return movieDao.findByGenre(genre);
    }

    @Override
    public List<Movie> findByDuration(int durationFrom, int durationTo) {
        return movieDao.findByDuration(durationFrom, durationTo);
    }

    @Override
    public List<Movie> findByDurationAndGenre(String genre,int durationFrom, int durationTo){
        return movieDao.findByDurationAndGenre(genre,durationFrom,durationTo);
    }

    @Override
    public Optional<Movie> findById(long id){
        return movieDao.findById(id);
    }

    @Override
    public List<Movie> getSearchedMovies(String query) {
        return movieDao.getSearchedMovies(query);
    }

    @Override
    public List<Movie> ordenByAsc(String parameter) {
        return movieDao.ordenByAsc(parameter);

    }

    @Override
    public List<Movie> ordenByDesc(String parameter) {
        return movieDao.ordenByDesc(parameter);
    }

}
