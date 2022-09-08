package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Movie;
import ar.edu.itba.paw.models.Serie;
import ar.edu.itba.paw.persistance.SerieDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SerieServiceImpl implements SerieService{

    private final SerieDao serieDao;

    @Autowired
    public SerieServiceImpl(final SerieDao serieDao) {
        this.serieDao = serieDao;
    }

    @Override
    public List<Serie> getAllSeries() {
        return serieDao.getAllSeries();
    }

    @Override
    public Optional<Serie> findByName(String name) {
        return serieDao.findByName(name);
    }

    @Override
    public Optional<Serie> findById(long id) {
        return serieDao.findById(id);
    }

    @Override
    public List<Serie> findByGenre(String genre) {
        return serieDao.findByGenre(genre);
    }

    @Override
    public List<Serie> findByDurationAndGenre(String genre,int durationFrom, int durationTo){
        return serieDao.findByDurationAndGenre(genre,durationFrom,durationTo);
    }

    @Override
    public List<Serie> findByDuration(int durationFrom, int durationTo) {
        return serieDao.findByDuration(durationFrom, durationTo);
    }

    @Override
    public List<Serie> getSearchedSeries(String query) {
        return serieDao.getSearchedSeries(query);
    }

    @Override
    public List<Serie> ordenByAsc(String parameter) {
        return serieDao.ordenByAsc(parameter);

    }

    @Override
    public List<Serie> ordenByDesc(String parameter) {
        return serieDao.ordenByDesc(parameter);
    }
}
