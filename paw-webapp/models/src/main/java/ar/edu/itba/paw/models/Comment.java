package ar.edu.itba.paw.models;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "userid", "reviewid" }) })

public class Comment {
    /* package */ Comment() {
// Just for Hibernate, we love you!
    }
    public Comment(User user, Review review,String text, LocalDateTime date){
        this.user=user;
        this.review=review;
        this.date=date;
        this.text=text;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "comment_id_seq")
    @SequenceGenerator(allocationSize = 1,sequenceName = "comment_id_seq",name = "comment_id_seq")
    @Column(name = "commentid")
    private long commentId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reviewid")
    private Review review;

    @OneToMany(orphanRemoval = true,fetch = FetchType.LAZY,mappedBy = "comment")
    private Set<CommentReport> reports;

    private String text;
    private LocalDateTime date;

    @Formula(value = "(SELECT count(*) from commentreport r where r.commentid=commentid)")
    private Integer reportAmount;
    public User getUser() {
        return user;
    }


    public Review getReview() {
        return review;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public Set<CommentReport> getReports() {
        return reports;
    }


    public void setReview(Review review) {
        this.review = review;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setReports(Set<CommentReport> reports) {
        this.reports = reports;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getCommentId() {
        return commentId;
    }
    @Transient
    public Integer getReportAmount() {
        return reportAmount;
    }
}
