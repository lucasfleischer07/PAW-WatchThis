package ar.edu.itba.paw.webapp.controller.queryParams;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.PageWrapper;
import ar.edu.itba.paw.models.Sorting;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.auth.SecurityChecks;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GetContentParams {
    private static final int CONTENT_AMOUNT = 20;

    public static PageWrapper<Content> getContentByParams(final String contentType,
                                                          Integer pageNum,
                                                          final String durationFrom,
                                                          final String durationTo,
                                                          final Sorting sorting,
                                                          final String query,
                                                          final String genre,
                                                          final Long watchListSavedBy,
                                                          final Long viewedListSavedBy,
                                                          ContentService cs,
                                                          UserService us,
                                                          SecurityChecks securityChecks) {
//        TODO: Terminar de chequear estos casos de cuando un query param tiene que ser null y otros no
        if(watchListSavedBy != null && (contentType != null || !Objects.equals(durationFrom, "ANY") || !Objects.equals(durationTo, "ANY") || sorting != null || !Objects.equals(query, "ANY") || genre != null || viewedListSavedBy != null)) {
            throw new InvalidParameterException("Invalid parameters");
        } else if (viewedListSavedBy != null && (contentType != null || !Objects.equals(durationFrom, "ANY") || !Objects.equals(durationTo, "ANY") || sorting != null || !Objects.equals(query, "ANY") || genre != null || watchListSavedBy != null)) {
            throw new InvalidParameterException("Invalid parameters");
        }

        PageWrapper<Content> contentListFilter;

        if(watchListSavedBy != null) {
            securityChecks.checkUser(watchListSavedBy);
            final User user = us.findById(watchListSavedBy).orElseThrow(UserNotFoundException::new);
            contentListFilter = us.getWatchList(user, pageNum, CONTENT_AMOUNT);
            return contentListFilter;
        }

        if(viewedListSavedBy != null) {
            securityChecks.checkUser(viewedListSavedBy);
            final User user = us.findById(viewedListSavedBy).orElseThrow(UserNotFoundException::new);
            contentListFilter = us.getUserViewedList(user, pageNum, CONTENT_AMOUNT);
            return contentListFilter;
        }

        if(!contentType.equals("movie") && !contentType.equals("serie") && !contentType.equals("all")){
            throw new PageNotFoundException();
        }

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
