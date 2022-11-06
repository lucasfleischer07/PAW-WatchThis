package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Report;
import ar.edu.itba.paw.models.ReportReason;

import java.util.List;

public interface ReportDao {

    void delete(Object reviewOrComment);
    void removeReports(Object reviewOrComment);
    void addReport(Object reviewOrComment, ReportReason reason, String text);
    List<Report> getReportedReviews();
    List<Report> getReportedComments();
}
