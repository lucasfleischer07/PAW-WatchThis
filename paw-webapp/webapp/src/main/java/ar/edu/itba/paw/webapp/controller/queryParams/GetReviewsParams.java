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
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.ForbiddenException;
import java.security.InvalidParameterException;
import java.util.List;

public class GetReviewsParams {
    private static final int REVIEW_AMOUNT = 3;
    public static PageWrapper<Review> getReviewsByParams(final Long userId,
                                                         final Long contentId,
                                                         final Long reportedById,
                                                         final int page,
                                                         UserService us,
                                                         ContentService cs,
                                                         ReviewService rs) {
        if((contentId == null && userId == null && reportedById == null) || (contentId != null && userId != null && reportedById != null) || (contentId != null && userId == null && reportedById != null) || (contentId == null && userId != null && reportedById != null) || ( userId != null && userId < 0) || ( reportedById != null && reportedById < 0)) {
            throw new InvalidParameterException();
        }
        PageWrapper<Review> reviewList = null;
        if(contentId != null && userId == null) {
            Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
            reviewList = rs.getAllReviews(content, page, REVIEW_AMOUNT);
        } else if(contentId != null && userId != null) {
            Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
            reviewList = rs.getAllReviewsSorted(content, page, REVIEW_AMOUNT,userId);
        } else if(reportedById != null) {
                List<Review> reportedReviews=us.findById(reportedById).orElseThrow(UserNotFoundException::new).getReportedReviewsList();
                return new PageWrapper<Review>(page,1,reportedReviews.size(),reportedReviews,reportedReviews.size());
        } else {
            final User user = us.findById(userId).orElseThrow(UserNotFoundException::new);
            reviewList = rs.getAllUserReviews(user, page, REVIEW_AMOUNT);
        }
        return reviewList;

    }

}
