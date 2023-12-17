package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.PageWrapper;
import ar.edu.itba.paw.models.Sorting;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.ContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Transactional
@Service
public class ContentServiceImpl implements ContentService {

    private final ContentDao ContentDao;
    @Autowired
    private UserService us;

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

//    @Override
//    public List<List<Content>> getLandingPageContent(User user) {
//        List<List<Content>> mainList = new ArrayList<>();
//        List<Content> bestRatedList = getBestRated();
//        List<Content> lastAddedList = getLastAdded();
//        mainList.add(bestRatedList);
//        mainList.add(lastAddedList);
//        if(user == null) {
//            List<Content> mostSavedContentByUsersList = getMostUserSaved();
//            mainList.add(mostSavedContentByUsersList);
//        } else {
//            List<Long> userWatchListContentId = us.getUserWatchListContent(user);
//            if(userWatchListContentId.size() != 0) {
//                List<Content> recommendedUserList = getUserRecommended(user);
//                if (recommendedUserList.size() == 0) {
//                    mainList.add(Collections.emptyList());   // Esta seria la de recommended que esta vacia
//                    List<Content> mostSavedContentByUsersList = getMostUserSaved();
//                    mainList.add(mostSavedContentByUsersList);
//                } else {
//                    mainList.add(recommendedUserList);
//                    mainList.add(Collections.emptyList());   // Esta seria la de most que esta vacia
//                }
//            } else {
//                mainList.add(Collections.emptyList());   // Esta seria la de recommended que esta vacia
//                List<Content> mostSavedContentByUsersList = getMostUserSaved();
//                mainList.add(mostSavedContentByUsersList);
//            }
//        }
//        return mainList;
//    }

    @Override
    public PageWrapper<Content> getMasterContent(String type, List<String> genres, String durationFrom, String durationTo, Sorting sort, String queryUser, int page, int pageSize){
        String genre = getGenreQuery(genres);
        if (!Objects.equals(genre, "ANY") && Objects.equals(durationFrom, "ANY") && Objects.equals(queryUser, "ANY")) {
            return findByGenre(type, genre, sort,page,pageSize);
        } else if (Objects.equals(genre, "ANY") && !Objects.equals(durationFrom, "ANY") && Objects.equals(queryUser, "ANY")) {
            return findByDuration(type, Integer.parseInt(durationFrom), Integer.parseInt(durationTo), sort,page,pageSize);
        } else if(Objects.equals(genre, "ANY") && Objects.equals(durationFrom, "ANY") && !Objects.equals(queryUser, "ANY")){
            return getSearchedContent(type, queryUser,sort,page,pageSize);
        } else if (!Objects.equals(durationFrom, "ANY") && !Objects.equals(genre, "ANY") && Objects.equals(queryUser, "ANY")) {    // Caso de que si los filtros estan vacios
            return findByDurationAndGenre(type, genre, Integer.parseInt(durationFrom), Integer.parseInt(durationTo), sort,page,pageSize);
        } else if (!Objects.equals(genre, "ANY") && Objects.equals(durationFrom, "ANY") && !Objects.equals(queryUser, "ANY")) {
            return getSearchedContentByGenre(type, genre, sort, queryUser,page,pageSize);
        } else if (Objects.equals(genre, "ANY") && !Objects.equals(durationFrom, "ANY") && !Objects.equals(queryUser, "ANY")) {
            return getSearchedContentByDuration(type, Integer.parseInt(durationFrom), Integer.parseInt(durationTo), sort, queryUser,page,pageSize);
        } else if(!Objects.equals(genre, "ANY") && !Objects.equals(durationFrom, "ANY") && !Objects.equals(queryUser, "ANY")){
            return getSearchedContentByDurationAndGenre(type,genre, Integer.parseInt(durationFrom), Integer.parseInt(durationTo), sort, queryUser,page,pageSize);
        } else{
            return getAllContent(type, sort,page,pageSize);
        }
    }

    @Override
    public PageWrapper<Content> getAllContent(String type, Sorting sort, int page, int pageSize) {
        return ContentDao.getAllContent(type, sort,page,pageSize);
    }

    @Override
    public Optional<Content> findByName(String name) {
        return ContentDao.findByName(name);
    }

    @Override
    public PageWrapper<Content> findByGenre(String type, String genre, Sorting sort, int page, int pageSize) {
        return ContentDao.findByGenre(type, genre, sort,page,pageSize);
    }

    @Override
    public PageWrapper<Content> findByDuration(String type, int durationFrom, int durationTo, Sorting sort, int page, int pageSize) {
        return ContentDao.findByDuration(type, durationFrom, durationTo, sort,page,pageSize);
    }

    @Override
    public PageWrapper<Content> findByDurationAndGenre(String type, String genre, int durationFrom, int durationTo, Sorting sort, int page, int pageSize){
        return ContentDao.findByDurationAndGenre(type, genre,durationFrom,durationTo, sort,page,pageSize);
    }

    @Override
    public Optional<Content> findById(long id){
        return ContentDao.findById(id);
    }

    @Override
    public PageWrapper<Content> getSearchedContent(String type, String query, Sorting sort,int page, int pageSize) {
        return ContentDao.getSearchedContent(type,query,sort,page,pageSize);
    }

    @Override
    public PageWrapper<Content> getSearchedContentByGenre(String type, String genre, Sorting sort, String queryUser, int page, int pageSize){
        return ContentDao.getSearchedContentByGenre(type,genre,sort,queryUser,page,pageSize);
    }

    @Override
    public PageWrapper<Content> getSearchedContentByDuration(String type, int durationFrom, int durationTo, Sorting sort, String queryUser, int page, int pageSize){
        return ContentDao.getSearchedContentByDuration(type,durationFrom,durationTo,sort,queryUser,page,pageSize);
    }

    @Override
    public PageWrapper<Content> getSearchedContentByDurationAndGenre(String type, String genre, int durationFrom, int durationTo, Sorting sort, String queryUser, int page, int pageSize){
        return ContentDao.getSearchedContentByDurationAndGenre(type,genre,durationFrom,durationTo,sort,queryUser,page,pageSize);
    }


    @Override
    public PageWrapper<Content> getSearchedContentRandom(String query, int page, int pageSize) {
        return ContentDao.getSearchedContentRandom(query, page, pageSize);
    }

    @Override
    public PageWrapper<Content> getBestRated(int page, int pageSize) {
        return ContentDao.getBestRated(page, pageSize);
    }

    @Override
    public PageWrapper<Content> getUserRecommended(User user, int page, int pageSize) {
        return  ContentDao.getUserRecommended(user, page, pageSize);
    }

    @Override
    public PageWrapper<Content> getMostUserSaved(int page, int pageSize) {
        return  ContentDao.getMostUserSaved(page, pageSize);
    }


    @Override
    public PageWrapper<Content> getLastAdded(int page, int pageSize) {
        return ContentDao.getLastAdded(page, pageSize);
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
        if(contentImage.length == 0) {
            ContentDao.updateContent(id,name,description,releaseDate,genre,creator,duration,durationString,type);
        } else {
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
            genresBuilder.append(",");
        }
        genresBuilder.deleteCharAt(genresBuilder.length()-1);
        return genresBuilder.toString();
    }

    @Override
    public List<User> getContentReviewers(Long id) {
        return ContentDao.getContentReviewers(id);
    }

    @Override
    public String getContentImageHash(Content content){
        byte[] hash;
        try{
            hash= MessageDigest.getInstance("SHA-256").digest(content.getImage());

        }catch (NoSuchAlgorithmException e){
            hash=null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
