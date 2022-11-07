package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
public class CommentReport  {
        /* package */ CommentReport() {
// Just for Hibernate, we love you!
        }
        public CommentReport(User user, Comment comment, ReportReason reportReason){
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

        @Transient
        private final String type="comment";


        @Enumerated(EnumType.STRING)
        private ReportReason reportReason;

        public User getUser() {
                return user;
        }

        public Comment getComment() {
                return comment;
        }

        public String getType() {
                return type;
        }

        public ReportReason getReportReason() {
                return reportReason;
        }

        public CommentReportKey getId() {
                return id;
        }
}
