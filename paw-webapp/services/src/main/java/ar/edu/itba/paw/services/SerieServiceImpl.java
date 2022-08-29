package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Serie;
import ar.edu.itba.paw.persistance.SerieDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SerieServiceImpl implements SerieService{

    private final SerieDao serieDao;

    @Autowired
    public SerieServiceImpl(final SerieDao serieDao) {
        this.serieDao = serieDao;
    }

    @Override
    public Optional<Serie> findByName(String name) {
        return serieDao.findByName(name);
    }

    @Override
    public Optional<Serie> findByGenre(String genre) {
        return serieDao.findByGenre(genre);
    }

    @Override
    public Optional<Serie> findByDuration(String duration) {
        return serieDao.findByDuration(duration);
    }
}