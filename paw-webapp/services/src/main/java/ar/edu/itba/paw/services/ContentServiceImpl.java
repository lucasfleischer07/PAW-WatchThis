package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.persistance.ContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Multipart;
import java.util.List;
import java.util.Optional;

@Service
public class ContentServiceImpl implements ContentService {

    private final ContentDao ContentDao;

    @Autowired
    public ContentServiceImpl(final ContentDao ContentDao) {
        this.ContentDao = ContentDao;
    }

    @Override
    public List<Content> getAllContent(String type, String sort) {
        return ContentDao.getAllContent(type, sort);
    }

    @Override
    public Optional<Content> findByName(String name) {
        return ContentDao.findByName(name);
    }

    @Override
    public List<Content> findByGenre(String type, String genre, String sort) {
        return ContentDao.findByGenre(type, genre, sort);
    }

    @Override
    public List<Content> findByDuration(String type, int durationFrom, int durationTo, String sort) {
        return ContentDao.findByDuration(type, durationFrom, durationTo, sort);
    }

    @Override
    public List<Content> findByDurationAndGenre(String type, String genre,int durationFrom, int durationTo, String sort){
        return ContentDao.findByDurationAndGenre(type, genre,durationFrom,durationTo, sort);
    }

    @Override
    public Optional<Content> findById(long id){
        return ContentDao.findById(id);
    }

    @Override
    public List<Content> getSearchedContent(String query) {
        return ContentDao.getSearchedContent(query);
    }

    @Override
    public List<Content> getSearchedContentRandom(String query) {
        return ContentDao.getSearchedContentRandom(query);
    }

    @Override
    public void addContentPoints(long contentId,int rating){
        ContentDao.addContentPoints(contentId,rating);
    }

    @Override
    public void decreaseContentPoints(long contentId,int rating){
        ContentDao.decreaseContentPoints(contentId,rating);
    }

    @Override
    public List<Content> getBestRated() {
        return ContentDao.getBestRated();
    }

    @Override
    public List<Content> getLessDuration(String type) {
        return ContentDao.getLessDuration(type);
    }

    @Override
    public List<Content> getLastAdded() {
        return ContentDao.getLastAdded();
    }

    @Override
    public void contentCreate(String name, String description, String releaseDate, String genre, String creator, Integer duration, String type, byte[] contentImage){
        String durationString = String.format("%d:%d",duration/60,duration - (duration/60)*60);

        ContentDao.contentCreate(name,description,releaseDate,genre,creator,duration,durationString,type,contentImage);
    }
}
