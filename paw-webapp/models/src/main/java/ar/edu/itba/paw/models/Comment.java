package ar.edu.itba.paw.models;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Comment {
    /* package */ Comment() {
// Just for Hibernate, we love you!
    }
    public Comment(User user, Review review,String text){
        this.user=user;
        this.review=review;
        this.text=text;
        this.commentId=0;
    }

    public Comment(long commentId, User user, Review review, String text) {
        this.commentId = commentId;
        this.user = user;
        this.review = review;
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "comment_id_seq")
    @SequenceGenerator(allocationSize = 1,sequenceName = "comment_id_seq",name = "comment_id_seq")
    @Column(name = "commentid")
    private long commentId;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "reviewid")
    private Review review;

    @OneToMany(orphanRemoval = true,fetch = FetchType.LAZY,mappedBy = "comment")
    private Set<CommentReport> commentReports;
    @Column(length = 500)
    private String text;
    @Formula(value = "(select coalesce(string_agg(u.name,' '),'') from Userdata u join CommentReport r on u.userid=r.userid where r.commentid=commentid)")
    private String reporterUsernames;
    @Transient
    private int reportAmount=0;
    @Formula(value = "(select coalesce(string_agg(r.reportreason,' '),'') from CommentReport r  where r.commentid=commentid)")
    private String reportReasons;
    @PostLoad
    private void onLoad(){

        reportAmount=commentReports.size();
    }
    public User getUser() {
        return user;
    }


    public Review getReview() {
        return review;
    }


    public String getText() {
        return text;
    }

    public Set<CommentReport> getReports() {
        return commentReports;
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
    
    public long getCommentId() {
        return commentId;
    }
    @Transient
    public Integer getReportAmount() {
        return reportAmount;
    }

    public Set<CommentReport> getCommentReports() {
        return commentReports;
    }

    public String getReportReasons() {
        return reportReasons;
    }

    public void setCommentReports(Set<CommentReport> commentReports) {
        this.commentReports = commentReports;
    }

    @Transient
    public String getReporterUsernames() {
        return reporterUsernames;
    }
}
