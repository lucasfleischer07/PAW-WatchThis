package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
public class Comment {
    /* package */ Comment() {
// Just for Hibernate, we love you!
    }
    public Comment(User user, Review review, String text, LocalDateTime date){
        this.user=user;
        this.review=review;
        this.id=new CommentKey(user.getId(),review.getId());
        this.text=text;
        this.date=date;
    }
    @EmbeddedId
    private CommentKey id;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("reviewId")
    private Review review;

    @OneToMany(orphanRemoval = true,fetch = FetchType.LAZY,mappedBy = "comment")
    private Set<Report> reports;

    private String text;
    private LocalDateTime date;

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

    public CommentKey getId() {
        return id;
    }

    public Set<Report> getReports() {
        return reports;
    }
}
