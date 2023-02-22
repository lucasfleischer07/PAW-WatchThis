package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Sorting;
import ar.edu.itba.paw.models.User;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.util.List;
import java.util.Optional;

public interface ContentService {
    List<List<Content>> getLandingPageContent(User user);
    List<Content> getMasterContent(String type, List<String> genres, String durationFrom, String durationTo, Sorting sort, String queryUser);
    List<Content> getAllContent(String type, Sorting sort);
    Optional<Content> findByName(String name);
    List<Content> findByGenre(String type, String genre, Sorting sort);
    List<Content> findByDuration(String type, int durationFrom, int durationTo, Sorting sort);
    List<Content> findByDurationAndGenre(String type, String genre, int durationFrom, int durationTo, Sorting sort);
    Optional<Content> findById(long id);
    List<Content> getSearchedContent(String type,String query);
    List<Content> getSearchedContentByGenre(String type, String genre, Sorting sort,String queryUser);
    List<Content> getSearchedContentByDuration(String type, int durationFrom, int durationTo, Sorting sort,String queryUser);
    List<Content> getSearchedContentByDurationAndGenre(String type, String genre, int durationFrom, int durationTo, Sorting sort,String queryUser);
    List<Content> getSearchedContentRandom(String query);
    List<Content> getBestRated();
    List<Content> getUserRecommended(User user);
    List<Content> getMostUserSaved();
    List<Content> getLastAdded();
    Content contentCreate(String name, String description, String releaseDate, String genre, String creator, Integer duration, String type, byte[] contentImage);
    Optional<String> getContentQuote(String language);
    void updateContent(Long id,String name, String description, String releaseDate, String genre, String creator, Integer duration, String type,byte[] contentImage);
    void deleteContent(Long id);
    String getGenreString(List<String> genres);
    List<User> getContentReviewers(Long id);
}
