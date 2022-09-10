package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;

import java.util.List;
import java.util.Optional;

public interface ContentService {
    List<Content> getAllContent(String type);
    Optional<Content> findByName(String name);
    List<Content> findByGenre(String type, String genre);
    List<Content> findByDuration(String type, int durationFrom, int durationTo);
    List<Content> findByDurationAndGenre(String type, String genre,int durationFrom, int durationTo);
    Optional<Content> findById(long id);
    List<Content> getSearchedContent(String query);
    List<Content> ordenByAsc(String parameter);
    List<Content> ordenByDesc(String parameter);



}
