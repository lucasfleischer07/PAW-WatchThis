package ar.edu.itba.paw.webapp.controller.queryParams;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.ForbiddenException;
import java.security.InvalidParameterException;
import java.util.List;

public class GetCommentsParams {
    private static final int REPORTS_AMOUNT = 10;

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

    public static PageWrapper<CommentReport> getCommentReports(final Long reportId,
                                                               final ReportReason reason,
                                                               final Integer page,
                                                               ReportService rrs) {
        if((reportId != null && reason != null && page != null) || (reportId == null && reason == null && page == null) || (reportId != null && (reason != null || page != null)) || (reason != null && page == null)) {
            throw new InvalidParameterException("Invalid parameters");
        }

        if(reportId != null) {
            // TODO: Hacer en rrs una funcion para getear la review reportada por el id
//            return new PageWrapper<ReviewReport>(1,1,userDislikeReviewsList.size(),userDislikeReviewsList,userDislikeReviewsList.size());

        } else {
            return rrs.getReportedComments(reason, page, REPORTS_AMOUNT);
        }
        // TODO: Sacar este retun null cuando se haga la query de arriba, solo lo deje para que no tire error
        return null;

    }
}
