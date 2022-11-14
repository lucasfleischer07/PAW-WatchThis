package ar.edu.itba.paw.models;

import org.hibernate.annotations.Generated;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "userid", "commentid" }) })
public class CommentReport  {
        /* package */ CommentReport() {
// Just for Hibernate, we love you!
        }
        public CommentReport(User user, Comment comment, ReportReason reportReason){
            this.reportReason=reportReason;
            this.comment=comment;
            this.user=user;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "commentreport_id_seq")
        @SequenceGenerator(allocationSize = 1,sequenceName = "commentreport_id_seq",name = "commentreport_id_seq")
        private long id;

        @ManyToOne(optional = false)
        @JoinColumn(name = "userid")
        private User user;

        @ManyToOne(optional = false)
        @JoinColumn(name = "commentid")
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

        public long getId() {
                return id;
        }
}
