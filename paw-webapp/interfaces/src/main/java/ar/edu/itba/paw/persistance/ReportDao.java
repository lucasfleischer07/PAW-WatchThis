package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.*;

import java.util.List;
import java.util.Optional;

public interface ReportDao {

    void delete(Object reviewOrComment);
    void removeReports(Object reviewOrComment);
    void addReport(Object reviewOrComment, User user, ReportReason reason);
    Long getReportedContentId(Object reviewOrComment);
    PageWrapper<ReviewReport> getReportedReviews(int page,int pageSize);
    ReviewReport getReportedReview(Long reportId);
    PageWrapper<ReviewReport> getReportedReviewsByReason(ReportReason reason,int page,int pageSize);
    int getReportedReviewsAmount();
    int getReportedReviewsAmountByReason(ReportReason reason);
    PageWrapper<CommentReport> getReportedComments(int page,int pageSize);
    CommentReport getReportedComment(Long reportId);
    PageWrapper<CommentReport> getReportedCommentsByReason(ReportReason reason, int page, int pageSize);
    int getReportedCommentsAmount();
    int getReportedCommentsAmountByReason(ReportReason reason);
    Optional<CommentReport> findCommentReport(Long id);
    Optional<ReviewReport> findReviewReport(Long id);

}
