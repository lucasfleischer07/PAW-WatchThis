package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
public class CommentReport  {
        /* package */ CommentReport() {
// Just for Hibernate, we love you!
        }
        public CommentReport(User user, Comment comment, String text, ReportReason reportReason){
            this.comment=comment;
            this.id=new CommentReportKey(user.getId(), null);
        }
        @EmbeddedId
        private CommentReportKey id;

        @ManyToOne
        @MapsId("userId")
        private User user;

        @ManyToOne
        @MapsId("commentId")
        private Comment comment;


        private String text;
        @Enumerated(EnumType.STRING)
        private ReportReason reportReason;

}
