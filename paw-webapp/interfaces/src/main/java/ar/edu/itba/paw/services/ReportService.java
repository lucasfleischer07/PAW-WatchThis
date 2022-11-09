package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.CommentReport;
import ar.edu.itba.paw.models.ReviewReport;
import ar.edu.itba.paw.models.ReportReason;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Set;

public interface ReportService {
    void delete(Object reviewOrComment, Set<CommentReport> reasonsOfDelete);
    void removeReports(Object reviewOrComment);
    void addReport(Object reviewOrComment, User user, String reason);
    List<ReviewReport> getReportedReviews();
    List<CommentReport> getReportedComments();


}
