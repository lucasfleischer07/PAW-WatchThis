package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Report;
import ar.edu.itba.paw.models.ReportReason;

import java.util.List;

public interface ReportService {
    void delete(Object reviewOrComment);
    void removeReports(Object reviewOrComment);
    void addReport(Object reviewOrComment, ReportReason reason, String text);
    List<Report> getReportedReviews();
    List<Report> getReportedComments();


}
