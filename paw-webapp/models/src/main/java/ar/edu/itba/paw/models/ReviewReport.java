package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "userid", "reviewid" }) })
public class ReviewReport {
    /* package */ ReviewReport() {
// Just for Hibernate, we love you!
    }
    public ReviewReport(User user, Review review, ReportReason reportReason){

        this.review=review;
        this.user=user;
        this.reportReason=reportReason;
        }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "reviewreport_id_seq")
    @SequenceGenerator(allocationSize = 1,sequenceName = "reviewreport_id_seq",name = "reviewreport_id_seq")
    private long id;
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "reviewid")
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
