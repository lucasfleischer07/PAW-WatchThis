package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.PageWrapper;
import ar.edu.itba.paw.models.Sorting;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface ContentService {
//    List<List<Content>> getLandingPageContent(User user);
    PageWrapper<Content> getMasterContent(String type, List<String> genres, String durationFrom, String durationTo, Sorting sort, String queryUser, int page, int pageSize);
    PageWrapper<Content> getAllContent(String type, Sorting sort, int page, int pageSize);
    Optional<Content> findByName(String name);
    PageWrapper<Content> findByGenre(String type, String genre, Sorting sort, int page, int pageSize);
    PageWrapper<Content> findByDuration(String type, int durationFrom, int durationTo, Sorting sort, int page, int pageSize);
    PageWrapper<Content> findByDurationAndGenre(String type, String genre, int durationFrom, int durationTo, Sorting sort, int page, int pageSize);
    Optional<Content> findById(long id);
    PageWrapper<Content> getSearchedContent(String type, String query, int page, int pageSize);
    PageWrapper<Content> getSearchedContentByGenre(String type, String genre, Sorting sort, String queryUser, int page, int pageSize);
    PageWrapper<Content> getSearchedContentByDuration(String type, int durationFrom, int durationTo, Sorting sort, String queryUser, int page, int pageSize);
    PageWrapper<Content> getSearchedContentByDurationAndGenre(String type, String genre, int durationFrom, int durationTo, Sorting sort, String queryUser, int page, int pageSize);
    PageWrapper<Content> getSearchedContentRandom(String query, int page, int pageSize);
    PageWrapper<Content> getBestRated(int page, int pageSize);
    PageWrapper<Content> getUserRecommended(User user, int page, int pageSize);
    PageWrapper<Content> getMostUserSaved(int page, int pageSize);
    PageWrapper<Content> getLastAdded(int page, int pageSize);
    Content contentCreate(String name, String description, String releaseDate, String genre, String creator, Integer duration, String type, byte[] contentImage);
    Optional<String> getContentQuote(String language);
    void updateContent(Long id,String name, String description, String releaseDate, String genre, String creator, Integer duration, String type,byte[] contentImage);
    void deleteContent(Long id);
    String getGenreString(List<String> genres);
    List<User> getContentReviewers(Long id);
    String getContentImageHash(Content content);
}
