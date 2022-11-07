package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
public class ReviewReport {
    /* package */ ReviewReport() {
// Just for Hibernate, we love you!
    }
    public ReviewReport(User user, Review review, String text, ReportReason reportReason){
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


    public void setText(String text) {
        this.text = text;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setReview(Review review) {
        this.review = review;
    }


    public void setReportReason(ReportReason reportReason) {
        this.reportReason = reportReason;
    }
}
