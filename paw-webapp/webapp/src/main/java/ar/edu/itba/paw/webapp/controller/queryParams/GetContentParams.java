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
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GetContentParams {
    private static final int CONTENT_AMOUNT = 20;
    private static final int DEFAULT_PAGE = 1;

    public static PageWrapper<Content> getContentByParams(final String contentType,
                                                          Integer pageNum,
                                                          final String durationFrom,
                                                          final String durationTo,
                                                          final Sorting sorting,
                                                          final String query,
                                                          final String genre,
                                                          final Long watchListSavedBy,
                                                          final Long viewedListSavedBy,
                                                          final Boolean paginated,
                                                          ContentService cs,
                                                          UserService us,
                                                          SecurityChecks securityChecks) {
        if(watchListSavedBy != null && (contentType != null || !Objects.equals(durationFrom, "ANY") || !Objects.equals(durationTo, "ANY") || sorting != null || !Objects.equals(query, "ANY") || genre != null || viewedListSavedBy != null)) {
            throw new InvalidParameterException("Invalid parameters");
        } else if (viewedListSavedBy != null && (contentType != null || !Objects.equals(durationFrom, "ANY") || !Objects.equals(durationTo, "ANY") || sorting != null || !Objects.equals(query, "ANY") || genre != null || watchListSavedBy != null)) {
            throw new InvalidParameterException("Invalid parameters");
        }

        if((!paginated && pageNum != null) || (paginated && pageNum == null)) {
            throw new InvalidParameterException("Invalid parameters");
        }

        PageWrapper<Content> contentListFilter;

        if(watchListSavedBy != null) {
            securityChecks.checkUser(watchListSavedBy);
            final User user = us.findById(watchListSavedBy).orElseThrow(UserNotFoundException::new);
            if(paginated) {
                contentListFilter = us.getWatchList(user, pageNum, CONTENT_AMOUNT);
            } else {
                contentListFilter = us.getWatchList(user, DEFAULT_PAGE, Integer.MAX_VALUE);
            }
            return contentListFilter;

        }

        if(viewedListSavedBy != null) {
            securityChecks.checkUser(viewedListSavedBy);
            final User user = us.findById(viewedListSavedBy).orElseThrow(UserNotFoundException::new);
            if(paginated) {
                contentListFilter = us.getUserViewedList(user, pageNum, CONTENT_AMOUNT);
            } else {
                contentListFilter = us.getUserViewedList(user, DEFAULT_PAGE, Integer.MAX_VALUE);
            }
            return contentListFilter;
        }

        if(!contentType.equals("movie") && !contentType.equals("serie") && !contentType.equals("all") && !contentType.equals("bestRated") && !contentType.equals("lastAdded") && !contentType.equals("mostSavedContentByUsers") && !contentType.equals("recommendedUser")){
            throw new InvalidParameterException("Invalid parameters");
        }

        if((contentType.equals("bestRated") || contentType.equals("lastAdded") || contentType.equals("mostSavedContentByUsers") || contentType.equals("recommendedUser")) && (pageNum != null || paginated)){
            throw new InvalidParameterException("Invalid parameters");
        }

        switch (contentType) {
            case "bestRated":
                contentListFilter = cs.getBestRated(DEFAULT_PAGE, CONTENT_AMOUNT);
                return contentListFilter;
            case "lastAdded":
                contentListFilter = cs.getLastAdded(DEFAULT_PAGE, CONTENT_AMOUNT);
                return contentListFilter;
            case "mostSavedContentByUsers":
                contentListFilter = cs.getMostUserSaved(DEFAULT_PAGE, CONTENT_AMOUNT);
                return contentListFilter;
            case "recommendedUser":
                contentListFilter = cs.getUserRecommended(us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new), DEFAULT_PAGE, CONTENT_AMOUNT);
                return contentListFilter;
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
