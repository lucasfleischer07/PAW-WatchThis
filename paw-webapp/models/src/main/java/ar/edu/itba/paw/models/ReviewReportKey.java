package ar.edu.itba.paw.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ReviewReportKey implements Serializable {
    @Column(name = "userid")
    private long userId;

    @Column(name = "reviewid")
    private long reviewId;



    ReviewReportKey(Long userId, Long reviewId){
        this.userId=userId;
        this.reviewId=reviewId;
    }

    /* package */ ReviewReportKey() {
// Just for Hibernate, we love you!
    }


    public Long getReviewId() {
        return reviewId;
    }

    public Long getUserId() {
        return userId;
    }


    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}