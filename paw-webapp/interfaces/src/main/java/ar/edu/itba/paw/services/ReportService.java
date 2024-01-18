package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.*;

import java.util.List;
import java.util.Set;

public interface ReportService {
    void delete(Object reviewOrComment, Set<CommentReport> reasonsOfDelete);
    void removeReports(String type, Long contentId);
    Long addReport(Object reviewOrComment, User user, String reason);
    ReviewReport getReportedReview(Long reviewId);
    PageWrapper<ReviewReport> getReportedReviews(ReportReason reason,int page,int pageSize);
    int getReportedReviewsAmount(ReportReason reason);
    CommentReport getReportedComment(Long commentId);
    PageWrapper<CommentReport> getReportedComments(ReportReason reason,int page,int pageSize);
    int getReportedCommentsAmount(ReportReason reason);



}
