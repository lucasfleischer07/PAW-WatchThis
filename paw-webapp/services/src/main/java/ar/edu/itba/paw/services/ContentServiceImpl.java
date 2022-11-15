package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Sorting;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.ContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Transactional
@Service
public class ContentServiceImpl implements ContentService {

    private final ContentDao ContentDao;

    @Autowired
    public ContentServiceImpl(final ContentDao ContentDao) {
        this.ContentDao = ContentDao;
    }

    private String getGenreQuery(List<String> genreList){
        String genreFilterDao = "";

        if(genreList!=null && genreList.size() > 1) {
            for(int i = 0; i < genreList.size(); i++) {
                if(i == 0) {
                    genreFilterDao = "'%'|| '" + genreList.get(i) + "' ||'%' OR";
                } else if(i != genreList.size() - 1) {
                    genreFilterDao = genreFilterDao + " genre LIKE '%'|| '" + genreList.get(i) + "' ||'%' OR";
                } else {
                    genreFilterDao = genreFilterDao + " genre LIKE '%'|| '" + genreList.get(i) + "' ||'%'";
                }
            }
        } else if(genreList!=null && genreList.size() == 1) {
            genreFilterDao = "'%'|| '" + genreList.get(0) + "' ||'%'";
        } else {
            genreFilterDao = "ANY";
        }
        return genreFilterDao;
    }

    @Override
    public List<Content> getMasterContent(String type, List<String> genres, String durationFrom, String durationTo, Sorting sort, String queryUser){
        String genre = getGenreQuery(genres);
        if (!Objects.equals(genre, "ANY") && Objects.equals(durationFrom, "ANY") && Objects.equals(queryUser, "ANY")) {
            return findByGenre(type, genre, sort);
        } else if (Objects.equals(genre, "ANY") && !Objects.equals(durationFrom, "ANY") && Objects.equals(queryUser, "ANY")) {
            return findByDuration(type, Integer.parseInt(durationFrom), Integer.parseInt(durationTo), sort);
        } else if(Objects.equals(genre, "ANY") && Objects.equals(durationFrom, "ANY") && !Objects.equals(queryUser, "ANY")){
            return getSearchedContent(type, queryUser);
        } else if (!Objects.equals(durationFrom, "ANY") && !Objects.equals(genre, "ANY") && Objects.equals(queryUser, "ANY")) {    // Caso de que si los filtros estan vacios
            return findByDurationAndGenre(type, genre, Integer.parseInt(durationFrom), Integer.parseInt(durationTo), sort);
        } else if (!Objects.equals(genre, "ANY") && Objects.equals(durationFrom, "ANY") && !Objects.equals(queryUser, "ANY")) {
            return getSearchedContentByGenre(type, genre, sort, queryUser);
        } else if (Objects.equals(genre, "ANY") && !Objects.equals(durationFrom, "ANY") && !Objects.equals(queryUser, "ANY")) {
            return getSearchedContentByDuration(type, Integer.parseInt(durationFrom), Integer.parseInt(durationTo), sort, queryUser);
        } else if(!Objects.equals(genre, "ANY") && !Objects.equals(durationFrom, "ANY") && !Objects.equals(queryUser, "ANY")){
            return getSearchedContentByDurationAndGenre(type,genre, Integer.parseInt(durationFrom), Integer.parseInt(durationTo), sort, queryUser);
        } else{
            return getAllContent(type, sort);
        }
    }

    @Override
    public List<Content> getAllContent(String type, Sorting sort) {
        return ContentDao.getAllContent(type, sort);
    }

    @Override
    public Optional<Content> findByName(String name) {
        return ContentDao.findByName(name);
    }

    @Override
    public List<Content> findByGenre(String type, String genre, Sorting sort) {
        return ContentDao.findByGenre(type, genre, sort);
    }

    @Override
    public List<Content> findByDuration(String type, int durationFrom, int durationTo, Sorting sort) {
        return ContentDao.findByDuration(type, durationFrom, durationTo, sort);
    }

    @Override
    public List<Content> findByDurationAndGenre(String type, String genre,int durationFrom, int durationTo, Sorting sort){
        return ContentDao.findByDurationAndGenre(type, genre,durationFrom,durationTo, sort);
    }

    @Override
    public Optional<Content> findById(long id){
        return ContentDao.findById(id);
    }

    @Override
    public List<Content> getSearchedContent(String type,String query) {
        return ContentDao.getSearchedContent(type,query);
    }

    @Override
    public List<Content> getSearchedContentByGenre(String type, String genre, Sorting sort,String queryUser){
        return ContentDao.getSearchedContentByGenre(type,genre,sort,queryUser);
    }

    @Override
    public List<Content> getSearchedContentByDuration(String type, int durationFrom, int durationTo, Sorting sort,String queryUser){
        return ContentDao.getSearchedContentByDuration(type,durationFrom,durationTo,sort,queryUser);
    }

    @Override
    public List<Content> getSearchedContentByDurationAndGenre(String type, String genre, int durationFrom, int durationTo, Sorting sort,String queryUser){
        return ContentDao.getSearchedContentByDurationAndGenre(type,genre,durationFrom,durationTo,sort,queryUser);
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
    public Content contentCreate(String name, String description, String releaseDate, String genre, String creator, Integer duration, String type, byte[] contentImage){
        String durationString = formatDuration(duration);
        return ContentDao.contentCreate(name,description,releaseDate,genre,creator,duration,durationString,type,contentImage);
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

    @Override
    public String getGenreString(List<String> genres){
        if(genres==null || genres.size()==0)
            return "ANY";
        StringBuilder genresBuilder = new StringBuilder();
        for (String subGenre : genres) {
            genresBuilder.append(subGenre);
            genresBuilder.append(" ");
        }
        genresBuilder.deleteCharAt(genresBuilder.length()-1);
        return genresBuilder.toString();
    }

}
