package ar.edu.itba.paw.webapp.controller.queryParams;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.CommentService;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.ReviewService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.ForbiddenException;
import java.security.InvalidParameterException;
import java.util.List;

public class GetCommentsParams {
    private static final int REVIEW_AMOUNT = 3;

    public static List<Comment> getCommentsByParams(final Long reportedById,
                                                    final Long reviewId,
                                                    UserService us,
                                                    CommentService ccs,
                                                    ReviewService rs) {
        if((reviewId == null && reportedById == null) || (reviewId != null && reportedById != null)) {
            throw new InvalidParameterException();
        }

        List<Comment> commentList = null;
        if(reviewId != null) {
            Review review = rs.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
            commentList = ccs.getReviewComments(review);
        } else if(reportedById!=null){
            commentList=us.findById(reportedById).orElseThrow(UserNotFoundException::new).getReportedCommentsList();
        }

        return commentList;

    }
}
