package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.PageWapper;
import ar.edu.itba.paw.models.Sorting;
import ar.edu.itba.paw.models.User;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.util.List;
import java.util.Optional;

public interface ContentDao {
    PageWapper<Content> getAllContent(String type, Sorting sort, int page, int pageSize);
    Optional<Content> findByName(String name);
    PageWapper<Content> findByGenre(String type, String genre, Sorting sort,int page, int pageSize);
    PageWapper<Content> findByDuration(String type, int durationFrom, int durationTo, Sorting sort,int page, int pageSize);
    PageWapper<Content> findByDurationAndGenre(String type, String genre,int durationFrom, int durationTo, Sorting sort,int page, int pageSize);
    Optional<Content> findById(long id);
    PageWapper<Content> getSearchedContent(String type,String queryUser,int page, int pageSize);
    PageWapper<Content> getSearchedContentRandom(String query,int page, int pageSize);
    PageWapper<Content> getSearchedContentByGenre(String type, String genre, Sorting sort,String queryUser,int page, int pageSize);
    PageWapper<Content> getSearchedContentByDuration(String type, int durationFrom, int durationTo, Sorting sort,String queryUser,int page, int pageSize);
    PageWapper<Content> getSearchedContentByDurationAndGenre(String type, String genre, int durationFrom, int durationTo, Sorting sort,String queryUser,int page, int pageSize);
    PageWapper<Content> findByType(String type);
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
