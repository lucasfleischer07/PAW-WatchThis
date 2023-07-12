package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.*;

import java.util.List;
import java.util.Set;

public interface ReportService {
    void delete(Object reviewOrComment, Set<CommentReport> reasonsOfDelete);
    void removeReports(String type, Long contentId);
    void addReport(Object reviewOrComment, User user, String reason);
    PageWrapper<ReviewReport> getReportedReviews(ReportReason reason,int page,int pageSize);
    int getReportedReviewsAmount(ReportReason reason);
    PageWrapper<CommentReport> getReportedComments(ReportReason reason,int page,int pageSize);
    int getReportedCommentsAmount(ReportReason reason);



}
