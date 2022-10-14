package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.ContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
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
    public List<Content> getBestRated() {
        return ContentDao.getBestRated();
    }

    @Override
    public List<Content> getUserRecommended(User user) {
        return  ContentDao.getUserRecommended(user);
    }

    @Override
    public List<Content> getMostUserSaved() {
        return  ContentDao.getMostUserSaved();
    }


    @Override
    public List<Content> getLastAdded() {
        return ContentDao.getLastAdded();
    }

    @Override
    public void contentCreate(String name, String description, String releaseDate, String genre, String creator, Integer duration, String type, byte[] contentImage){
        String durationString = formatDuration(duration);

        ContentDao.contentCreate(name,description,releaseDate,genre,creator,duration,durationString,type,contentImage);
    }

    @Override
    public Optional<String> getContentQuote(String language) {
        return ContentDao.getContentQuote(language);
    }

    @Override
    public void updateContent(Long id,String name, String description, String releaseDate, String genre, String creator, Integer duration, String type,byte[] contentImage){
        String durationString = formatDuration(duration);
        if(contentImage.length==0){
            ContentDao.updateContent(id,name,description,releaseDate,genre,creator,duration,durationString,type);
        }else{
            ContentDao.updateWithImageContent(id,name,description,releaseDate,genre,creator,duration,durationString,type,contentImage);
        }
    }

    private String formatDuration(int duration){
        if(duration >= 60){
            return String.format("%d hours %d minutes",duration/60,duration - (duration/60)*60);
        }
        return String.format("%d minutes",duration);

    }

    @Override
    public void deleteContent(Long id){
        ContentDao.deleteContent(id);
    }


}
