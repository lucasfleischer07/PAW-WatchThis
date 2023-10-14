package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.CommentReport;
import ar.edu.itba.paw.models.ReportReason;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.ReviewReport;

import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.stream.Collectors;

public class ReviewReportDto {

    private long id;
    private String user;
    private String review;
    private String content;
    private final String type="comment";
    private ReportReason reportReason;

    private String eliminateReview;
    private String dismissReport;


    public static Collection<ReviewReportDto> mapReviewReportToReviewReportDto(UriInfo uriInfo, Collection<ReviewReport> reviewReports) {
        return reviewReports.stream().map(rr -> new ReviewReportDto(uriInfo, rr)).collect(Collectors.toList());
    }

    public ReviewReportDto() {
        // For Jersey
    }

    public ReviewReportDto(UriInfo uriInfo, ReviewReport reviewReport){
        this.id = reviewReport.getId();
        this.user = uriInfo.getBaseUriBuilder().path("users").path(String.valueOf(reviewReport.getUser().getId())).build().toString();
        this.review =  uriInfo.getBaseUriBuilder().path("reviews").path(String.valueOf(reviewReport.getReview().getId())).build().toString();
        this.content =  uriInfo.getBaseUriBuilder().path("content").path(String.valueOf(reviewReport.getReview().getContent().getId())).build().toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getType() {
        return type;
    }

    public ReportReason getReportReason() {
        return reportReason;
    }

    public void setReportReason(ReportReason reportReason) {
        this.reportReason = reportReason;
    }

    public String getEliminateReview() {
        return eliminateReview;
    }

    public void setEliminateReview(String eliminateReview) {
        this.eliminateReview = eliminateReview;
    }

    public String getDismissReport() {
        return dismissReport;
    }

    public void setDismissReport(String dismissReport) {
        this.dismissReport = dismissReport;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
