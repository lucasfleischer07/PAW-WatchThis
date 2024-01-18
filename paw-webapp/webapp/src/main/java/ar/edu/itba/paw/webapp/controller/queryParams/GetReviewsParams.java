package ar.edu.itba.paw.webapp.controller.queryParams;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.ReportService;
import ar.edu.itba.paw.services.ReviewService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.auth.SecurityChecks;
import ar.edu.itba.paw.webapp.dto.response.ReviewDto;
import ar.edu.itba.paw.webapp.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class GetReviewsParams {
    private static final int REVIEW_AMOUNT = 3;

    public static PageWrapper<Review> getReviewsByParams(final Long userId,
                                                         final Long contentId,
                                                         final Long reportedById,
                                                         final int page,
                                                         final Long likeById,
                                                         final Long dislikeById,
                                                         UserService us,
                                                         ContentService cs,
                                                         ReviewService rs) {
        if(likeById != null || dislikeById != null) {
            if(userId != null) {
                throw new InvalidParameterException();
            }
            if(likeById != null) {
                return getReviewsLikes(likeById, us, rs);
            } else {
                return getReviewsDislikes(dislikeById, us, rs);
            }
        } else {
            return getReviews(userId, contentId, reportedById, page, us, cs, rs);
        }
    }

    private static PageWrapper<Review> getReviews(final Long userId,
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

    private static PageWrapper<Review> getReviewsLikes(final Long userId,
                                                       UserService us,
                                                       ReviewService rs) {

        User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        if(userId != loggedUser.getId()) {
            throw new ForbiddenException("Users dont match");
        }

        User user = us.findById(userId).orElseThrow(UserNotFoundException::new);
        Set<Review> userLikeReviews = rs.userLikeReviews(user.getUserVotes());
        List<Review> userLikeReviewsList = new ArrayList<>(userLikeReviews);
        return new PageWrapper<Review>(1,1,userLikeReviewsList.size(),userLikeReviewsList,userLikeReviewsList.size());
    }

    private static PageWrapper<Review> getReviewsDislikes(final Long userId,
                                                          UserService us,
                                                          ReviewService rs) {

        User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        if(userId != loggedUser.getId()) {
            throw new ForbiddenException("Users dont match");
        }

        User user = us.findById(userId).orElseThrow(UserNotFoundException::new);
        Set<Review> userDislikeReviews = rs.userDislikeReviews(user.getUserVotes());
        List<Review> userDislikeReviewsList = new ArrayList<>(userDislikeReviews);
        return new PageWrapper<Review>(1,1,userDislikeReviewsList.size(),userDislikeReviewsList,userDislikeReviewsList.size());
    }

}
