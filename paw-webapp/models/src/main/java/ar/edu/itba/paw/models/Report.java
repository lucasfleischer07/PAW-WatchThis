package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
public class Report {
    /* package */ Report() {
// Just for Hibernate, we love you!
    }
    public Report(User user, Object reviewOrComment, String text,ReportReason reportReason){
        if (reviewOrComment instanceof Review) {
            this.review=(Review) reviewOrComment;
            this.comment=null;
        }
        else if(reviewOrComment instanceof Comment){
            this.comment=(Comment) reviewOrComment;
            this.review=null;
        }
        else throw new IllegalArgumentException();
        this.user=user;
        this.reportReason=reportReason;
        this.text=text;

    }
    @Id
    private Long id;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("reviewId")
    private Review review;

    @ManyToOne
    @MapsId("commentId")
    private Comment comment;

    private String text;
    @Enumerated(EnumType.STRING)
    private ReportReason reportReason;


}
