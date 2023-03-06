package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.*;

import java.util.List;
import java.util.Optional;

public interface ReportDao {

    void delete(Object reviewOrComment);
    void removeReports(Object reviewOrComment);
    void addReport(Object reviewOrComment, User user, ReportReason reason);
    PageWrapper<ReviewReport> getReportedReviews(int page,int pageSize);
    PageWrapper<ReviewReport> getReportedReviewsByReason(ReportReason reason,int page,int pageSize);
    PageWrapper<CommentReport> getReportedComments(int page,int pageSize);
    PageWrapper<CommentReport> getReportedCommentsByReason(ReportReason reason, int page, int pageSize);
    Optional<CommentReport> findCommentReport(Long id);
    Optional<ReviewReport> findReviewReport(Long id);

}
