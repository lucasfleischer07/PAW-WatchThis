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
//        TODO: Terminar de chequear estos casos de cuando un query param tiene que ser null y otros no. Ver si hay que verificar los del contentType de bestRated y eso
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

        if(!contentType.equals("movie") && !contentType.equals("serie") && !contentType.equals("all") && !contentType.equals("bestRated") && !contentType.equals("lastAdded") && !contentType.equals("mostSavedContentByUsers") && !contentType.equals("recommendedUser")){
            throw new PageNotFoundException();
        }

        if((contentType.equals("bestRated") || contentType.equals("lastAdded") || contentType.equals("mostSavedContentByUsers") || contentType.equals("recommendedUser")) && (pageNum > 1)){
            throw new PageNotFoundException();
        }

        switch (contentType) {
            case "bestRated":
                contentListFilter = cs.getBestRated(pageNum, CONTENT_AMOUNT);
                return contentListFilter;
            case "lastAdded":
                contentListFilter = cs.getLastAdded(pageNum, CONTENT_AMOUNT);
                return contentListFilter;
            case "mostSavedContentByUsers":
                contentListFilter = cs.getMostUserSaved(pageNum, CONTENT_AMOUNT);
                return contentListFilter;
            case "recommendedUser":
//        TODO: HAcer este chequeo de que si es recommended, el usuario debe estar logueado. Creo que asi no esta del todo bien, pero nose si es 100% necesario pasarle el userId para esto
                contentListFilter = cs.getUserRecommended(us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new), pageNum, CONTENT_AMOUNT);
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
