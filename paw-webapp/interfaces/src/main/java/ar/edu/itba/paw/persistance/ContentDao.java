package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Content;

import java.util.List;
import java.util.Optional;

public interface ContentDao {
    List<Content> getAllContent();
    Optional<Content> findByName(String name);
    List<Content> findByGenre(String genre);
    List<Content> findByDuration(int durationFrom, int durationTo);
    List<Content> findByDurationAndGenre(String genre,int durationFrom, int durationTo);
    Optional<Content> findById(long id);
    List<Content> getSearchedContent(String query);
    List<Content> ordenByAsc(String parameter);
    List<Content> ordenByDesc(String parameter);
    List<Content> findByType(String type);




}
