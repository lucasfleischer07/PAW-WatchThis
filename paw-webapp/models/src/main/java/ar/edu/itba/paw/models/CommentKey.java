package ar.edu.itba.paw.models;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public  class CommentKey implements Serializable {

    @Column(name = "userid")
    private long userId;

    @Column(name = "reviewid")
    private long reviewId;

    CommentKey(Long userId, Long reviewId){
        this.userId=userId;
        this.reviewId=reviewId;
    }

    /* package */ CommentKey() {
// Just for Hibernate, we love you!
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getReviewId() {
        return reviewId;
    }
}
