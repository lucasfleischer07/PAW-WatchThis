package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
public class ReviewReport {
    /* package */ ReviewReport() {
// Just for Hibernate, we love you!
    }
    public ReviewReport(User user, Review review, ReportReason reportReason){
            this.review=review;
        }
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @MapsId
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne
    @MapsId
    @JoinColumn(name="reviewid")
    private Review review;


    @Enumerated(EnumType.STRING)
    private ReportReason reportReason;
    @Transient
    private final String type="review";

    public Review getReview() {
        return review;
    }

    public User getUser() {
        return user;
    }

    public ReportReason getReportReason() {
        return reportReason;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public String getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public void setReportReason(ReportReason reportReason) {
        this.reportReason = reportReason;
    }
}
