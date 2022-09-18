package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.persistance.ContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addContentPoints(int contentId,int rating){
        ContentDao.addContentPoints(contentId,rating);
    }

    @Override
    public void decreceContentPoints(int contentId,int rating){
        ContentDao.addContentPoints(contentId,rating);
    }

}
