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
    private UserDto user;
    private ReviewDto review;
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

    public ReviewReportDto(UriInfo url, ReviewReport reviewReport){
        //Este path deberia ir con un metodo delete
        this.eliminateReview = url.getBaseUriBuilder().path("review").path(String.valueOf(reviewReport.getReview().getId())).build().toString();
        //Este path tambien deberia ser con un metodo delete
        this.dismissReport = url.getBaseUriBuilder().path("report").path(String.valueOf(reviewReport.getId())).build().toString();
        this.id = reviewReport.getId();
        this.user = new UserDto(url,reviewReport.getUser());
        this.review = new ReviewDto(url,reviewReport.getReview());
        this.reportReason = reviewReport.getReportReason();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public ReviewDto getReview() {
        return review;
    }

    public void setReview(ReviewDto review) {
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
}
