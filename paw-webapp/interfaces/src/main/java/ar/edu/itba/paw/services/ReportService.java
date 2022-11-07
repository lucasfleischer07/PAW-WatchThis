package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.CommentReport;
import ar.edu.itba.paw.models.ReviewReport;
import ar.edu.itba.paw.models.ReportReason;

import java.util.List;

public interface ReportService {
    void delete(Object reviewOrComment,String reasonsOfDelete);
    void removeReports(Object reviewOrComment);
    void addReport(Object reviewOrComment, ReportReason reason, String text);
    List<ReviewReport> getReportedReviews();
    List<CommentReport> getReportedComments();


}
