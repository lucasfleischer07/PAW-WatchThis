package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
public class ReviewReport {
    /* package */ ReviewReport() {
// Just for Hibernate, we love you!
    }
    public ReviewReport(User user, Review review, String text, ReportReason reportReason){
            this.review=review;
            this.id=new ReviewReportKey(user.getId(), review.getId());
        }
    @EmbeddedId
    private ReviewReportKey id;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("reviewId")
    private Review review;


    private String text;
    @Enumerated(EnumType.STRING)
    private ReportReason reportReason;

    public Review getReview() {
        return review;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public ReportReason getReportReason() {
        return reportReason;
    }

    public ReviewReportKey getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public void setId(ReviewReportKey id) {
        this.id = id;
    }

    public void setReportReason(ReportReason reportReason) {
        this.reportReason = reportReason;
    }
}
