package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.CommentReport;
import ar.edu.itba.paw.models.ReviewReport;
import ar.edu.itba.paw.models.ReportReason;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface ReportDao {

    void delete(Object reviewOrComment);
    void removeReports(Object reviewOrComment);
    void addReport(Object reviewOrComment, User user, ReportReason reason);
    List<ReviewReport> getReportedReviews();
    List<ReviewReport> getReportedReviewsByReason(ReportReason reason);
    List<CommentReport> getReportedComments();
    List<CommentReport> getReportedCommentsByReason(ReportReason reason);
}
