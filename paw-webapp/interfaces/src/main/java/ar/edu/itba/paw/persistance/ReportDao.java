package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.CommentReport;
import ar.edu.itba.paw.models.ReviewReport;
import ar.edu.itba.paw.models.ReportReason;

import java.util.List;

public interface ReportDao {

    void delete(Object reviewOrComment);
    void removeReports(Object reviewOrComment);
    void addReport(Object reviewOrComment, ReportReason reason, String text);
    List<ReviewReport> getReportedReviews();
    List<CommentReport> getReportedComments();
}
