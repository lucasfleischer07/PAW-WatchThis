package ar.edu.itba.paw.webapp.controller.queryParams;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.PageWrapper;
import ar.edu.itba.paw.models.Sorting;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetContentParams {
    private static final int CONTENT_AMOUNT = 20;

    public static PageWrapper<Content> getContentByParams(final String contentType,
                                                          Integer pageNum,
                                                          final String durationFrom,
                                                          final String durationTo,
                                                          final Sorting sorting,
                                                          final String query,
                                                          final String genre,
                                                          ContentService cs) {
        if(!contentType.equals("movie") && !contentType.equals("serie") && !contentType.equals("all")){
            throw new PageNotFoundException();
        }
        PageWrapper<Content> contentListFilter;

        List<String> genreList = Arrays.asList(genre);
        if (genre == null || genre.equals("")){
            genreList = new ArrayList<>();
        } else {
            genreList = Arrays.asList(genre.split(","));
        }
        int page= pageNum;
        String auxType;

        contentListFilter = cs.getMasterContent(contentType, genreList, durationFrom, durationTo, sorting, query, page, CONTENT_AMOUNT);

        return contentListFilter;

    }
}
