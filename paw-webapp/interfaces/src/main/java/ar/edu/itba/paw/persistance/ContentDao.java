package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.PageWrapper;
import ar.edu.itba.paw.models.Sorting;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface ContentDao {
    PageWrapper<Content> getAllContent(String type, Sorting sort, int page, int pageSize);
    Optional<Content> findByName(String name);
    PageWrapper<Content> findByGenre(String type, String genre, Sorting sort, int page, int pageSize);
    PageWrapper<Content> findByDuration(String type, int durationFrom, int durationTo, Sorting sort, int page, int pageSize);
    PageWrapper<Content> findByDurationAndGenre(String type, String genre, int durationFrom, int durationTo, Sorting sort, int page, int pageSize);
    Optional<Content> findById(long id);
    PageWrapper<Content> getSearchedContent(String type, String queryUser, int page, int pageSize);
    PageWrapper<Content> getSearchedContentRandom(String query, int page, int pageSize);
    PageWrapper<Content> getSearchedContentByGenre(String type, String genre, Sorting sort, String queryUser, int page, int pageSize);
    PageWrapper<Content> getSearchedContentByDuration(String type, int durationFrom, int durationTo, Sorting sort, String queryUser, int page, int pageSize);
    PageWrapper<Content> getSearchedContentByDurationAndGenre(String type, String genre, int durationFrom, int durationTo, Sorting sort, String queryUser, int page, int pageSize);
    PageWrapper<Content> findByType(String type, int page, int pageSize);
    List<Content> getBestRated();
    List<Content> getUserRecommended(User user);
    List<Content> getMostUserSaved();
    List<Content> getLastAdded();
    Content contentCreate(String name, String description, String releaseDate, String genre, String creator, Integer duration,String durationString, String type, byte[] contentImage);
    Optional<String> getContentQuote(String language);
    void updateContent(Long id,String name, String description, String releaseDate, String genre, String creator, Integer duration,String durationString, String type);
    void updateWithImageContent(Long id,String name, String description, String releaseDate, String genre, String creator, Integer duration,String durationString, String type,byte[] contentImage);
    void deleteContent(Long id);
    List<User> getContentReviewers(Long id);
}
