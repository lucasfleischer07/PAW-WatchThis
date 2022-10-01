package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.util.List;
import java.util.Optional;

public interface ContentService {
    List<Content> getAllContent(String type, String sort);
    Optional<Content> findByName(String name);
    List<Content> findByGenre(String type, String genre, String sort);
    List<Content> findByDuration(String type, int durationFrom, int durationTo, String sort);
    List<Content> findByDurationAndGenre(String type, String genre, int durationFrom, int durationTo, String sort);
    Optional<Content> findById(long id);
    List<Content> getSearchedContent(String query);
    List<Content> getSearchedContentRandom(String query);
    List<Content> getBestRated();
    List<Content> getUserRecommended(User user);
    List<Content> getMostUserSaved();
    List<Content> getLastAdded();
    void contentCreate(String name, String description, String releaseDate, String genre, String creator, Integer duration, String type, byte[] contentImage);
    Optional<String> getContentQuote(String language);
    void updateContent(Long id,String name, String description, String releaseDate, String genre, String creator, Integer duration, String type,byte[] contentImage);
    void deleteContent(Long id);
}
