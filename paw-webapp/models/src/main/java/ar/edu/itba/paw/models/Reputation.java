package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
public class Reputation {
    /* package */ Reputation() {
// Just for Hibernate, we love you!
    }
    public Reputation(User user,Review review,boolean vote){
        this.user=user;
        this.review=review;
        this.upvote=vote;
        this.downvote=!vote;
        this.id=new ReputationKey(user.getId(),review.getId());
    }
    @EmbeddedId
    private ReputationKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("reviewId")
    @JoinColumn(name = "reviewid")
    private Review review;


    private boolean upvote;

    private boolean downvote;

    public Review getReview() {
        return review;
    }

    public User getUser() {
        return user;
    }


    public void setDownvote(boolean downvote) {
        this.downvote = downvote;
    }

    public void setUpvote(boolean upvote) {
        this.upvote = upvote;
    }

    public boolean isDownvote() {
        return downvote;
    }

    public boolean isUpvote() {
        return upvote;
    }
}
