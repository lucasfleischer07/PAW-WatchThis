package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Content;

import java.util.List;
import java.util.Optional;

public interface ContentDao {
    List<Content> getAllContent(String type, String sort);
    Optional<Content> findByName(String name);
    List<Content> findByGenre(String type, String genre, String sort);
    List<Content> findByDuration(String type, int durationFrom, int durationTo, String sort);
    List<Content> findByDurationAndGenre(String type, String genre,int durationFrom, int durationTo, String sort);
    Optional<Content> findById(long id);
    List<Content> getSearchedContent(String query);
    List<Content> getSearchedContentRandom(String query);
    List<Content> findByType(String type);
    void addContentPoints(long contentId,int rating);
    void decreaseContentPoints(long contentId,int rating);
    List<Content> getBestRated();
    List<Content> getLessDuration(String type);
}
