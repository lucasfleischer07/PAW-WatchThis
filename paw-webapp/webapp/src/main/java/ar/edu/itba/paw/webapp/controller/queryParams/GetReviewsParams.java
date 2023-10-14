package ar.edu.itba.paw.webapp.controller.queryParams;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.PageWrapper;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.ReviewService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;

import java.security.InvalidParameterException;

public class GetReviewsParams {
    private static final int REVIEW_AMOUNT = 3;

    public static PageWrapper<Review> getReviewsByParams(final Long userId,
                                           final Long contentId,
                                           final int pageNumber,
                                           UserService us,
                                           ContentService cs,
                                           ReviewService rs) {
        if((contentId == null && userId == null) || (contentId != null && userId != null)) {
            throw new InvalidParameterException();
        }
        PageWrapper<Review> reviewList;
        if(contentId != null) {
            Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
            reviewList = rs.getAllReviews(content, pageNumber, REVIEW_AMOUNT);
        } else {
            final User user = us.findById(userId).orElseThrow(UserNotFoundException::new);
            reviewList = rs.getAllUserReviews(user, pageNumber, REVIEW_AMOUNT);
        }
        return reviewList;

    }

    public static int getTotalReviews(final Long userId,
                                      UserService us,
                                      ReviewService rs) {
        if(userId == null) {
            throw new InvalidParameterException();
        }
        final User user = us.findById(userId).orElseThrow(UserNotFoundException::new);
        PageWrapper<Review> aux = rs.getAllUserReviews(user, 1, 1000000000);
        return aux.getPageContent().size();

    }
}
