package ar.edu.itba.paw.webapp.controller.queryParams;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.PageWrapper;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.ReviewService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.auth.SecurityChecks;
import ar.edu.itba.paw.webapp.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.ForbiddenException;
import java.security.InvalidParameterException;

public class GetReviewsParams {
    private static final int REVIEW_AMOUNT = 3;

    public static PageWrapper<Review> getReviewsByParams(final Long userId,
                                                         final Long contentId,
                                                         final Long reportedById,
                                                         final int pageNumber,
                                                         UserService us,
                                                         ContentService cs,
                                                         ReviewService rs) {
        if((contentId == null && userId == null && reportedById == null) || (contentId != null && userId != null && reportedById != null) || (contentId != null && userId != null && reportedById == null) || (contentId != null && userId == null && reportedById != null) || (contentId == null && userId != null && reportedById != null)) {
            throw new InvalidParameterException();
        }
        PageWrapper<Review> reviewList = null;
        if(contentId != null) {
            Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
            reviewList = rs.getAllReviews(content, pageNumber, REVIEW_AMOUNT);
        } else if(reportedById != null) {
            final User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
            if(loggedUser.getId() == reportedById) {
//                TODO: Setear aca lo de la lista de reviews reportadas (borrar la  lineas de abajo, esta puesta solo para que no tire error al correrlo)
                reviewList = rs.getAllUserReviews(loggedUser, pageNumber, REVIEW_AMOUNT);
            } else {
                throw new ForbiddenException();
            }
        } else {
            final User user = us.findById(userId).orElseThrow(UserNotFoundException::new);
            reviewList = rs.getAllUserReviews(user, pageNumber, REVIEW_AMOUNT);
        }
        return reviewList;

    }

}
