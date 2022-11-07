package ar.edu.itba.paw.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CommentReportKey implements Serializable {
    @Column(name = "userid",nullable = true)
    private Long userId;

    @Column(name = "commentid",nullable = true)
    private Long commentId;



    CommentReportKey(Long userId, Long commentId){
        this.userId=userId;
        this.commentId=commentId;
    }

    /* package */ CommentReportKey() {
// Just for Hibernate, we love you!
    }


    public Long getCommentId() {
        return commentId;
    }

    public Long getUserId() {
        return userId;
    }


    public void setCommentId(Long CommentId) {
        this.commentId = commentId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
