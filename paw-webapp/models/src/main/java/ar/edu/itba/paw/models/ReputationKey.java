package ar.edu.itba.paw.models;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ReputationKey implements Serializable {
    @Column(name = "userid")
    private long userId;

    @Column(name = "reviewid")
    private long reviewId;

    ReputationKey(long userId, long reviewId){
        this.userId=userId;
        this.reviewId=reviewId;
    }

    /* package */ ReputationKey() {
// Just for Hibernate, we love you!
    }
}