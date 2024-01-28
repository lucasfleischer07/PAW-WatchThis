package ar.edu.itba.paw.webapp.controller.queryParams;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.InvalidParameterNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.ForbiddenException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class GetCommentsParams {
    private static final int REPORTS_AMOUNT = 10;
    private static final int DEFAULT_PAGE = 1;

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

    public static PageWrapper<CommentReport> getReportsByParams(Integer page,
                                                                ReportReason reason,
                                                                final Long reportId,
                                                                ReportService rrs) {
        if((reportId != null && reason != null) || (reportId != null && page != null) || (reportId == null && reason == null && page == null)) {
            throw new InvalidParameterNotFoundException();
        }

        if(page == null && reportId == null) {
            page = DEFAULT_PAGE;
        }

        PageWrapper<CommentReport> reviewsReported;
        if(reportId == null) {
            reviewsReported = rrs.getReportedComments(reason, page, REPORTS_AMOUNT);
        } else {
            CommentReport commentReport = rrs.getReportedComment(reportId);
            List<CommentReport> commentReportList = new ArrayList<>();
            commentReportList.add(commentReport);
            return new PageWrapper<>(DEFAULT_PAGE, 1, commentReportList.size(), commentReportList, commentReportList.size());
        }

        return reviewsReported;
    }
}
