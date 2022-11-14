package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
@Transactional
public class ReportJpaDao implements ReportDao{
    @PersistenceContext
    private EntityManager em;
    @Override
    public void delete(Object reviewOrComment) {
        if(reviewOrComment instanceof Review){
            Review review=(Review) reviewOrComment;
            Content content=review.getContent();
            em.remove(review);
            em.merge(content);
        } else if(reviewOrComment instanceof Comment){
            Comment comment=(Comment) reviewOrComment;
            Review review=comment.getReview();
            em.remove(comment);
            em.merge(review);

        }
        else throw new IllegalArgumentException();
    }

    @Override
    public void removeReports(Object reviewOrComment) {
        if(reviewOrComment instanceof Review){
            Review review=(Review) reviewOrComment;
            for (ReviewReport report:review.getReports()
                 ) {
                em.remove(report);
            }
            em.merge(review);
        } else if(reviewOrComment instanceof Comment){
            Comment comment=(Comment) reviewOrComment;
            for (CommentReport report:comment.getReports()
                 ) {
                em.remove(report);
            }
            em.merge(comment);
        }
        else throw new IllegalArgumentException();
    }

    @Override
    public void addReport(Object reviewOrComment,User user, ReportReason reason) {
        if(reviewOrComment instanceof Review){
            Review review=(Review) reviewOrComment;
            ReviewReport toAdd =new ReviewReport(user,review,reason);
            em.persist(toAdd);
            em.merge(review);
        } else if(reviewOrComment instanceof Comment){
            Comment comment=(Comment) reviewOrComment;
            CommentReport toAdd =new CommentReport(user,comment,reason);
            em.persist(toAdd);
            em.merge(comment);
        }
        else throw new IllegalArgumentException();
    }

    @Override
    public List<ReviewReport> getReportedReviews() {
        TypedQuery<ReviewReport> query=em.createQuery("select r from ReviewReport r",ReviewReport.class);
        return query.getResultList();

    }

    @Override
    public List<ReviewReport> getReportedReviewsByReason(ReportReason reason){
        TypedQuery<ReviewReport>query= em.createQuery("select r from ReviewReport r where r.reportReason = :reason", ReviewReport.class);
        query.setParameter("reason",reason);
        return query.getResultList();
    }

    @Override
    public List<CommentReport> getReportedComments() {
        TypedQuery<CommentReport>query= em.createQuery("select r from CommentReport r", CommentReport.class);
        return query.getResultList();
    }

    @Override
    public List<CommentReport> getReportedCommentsByReason(ReportReason reason){
        TypedQuery<CommentReport>query= em.createQuery("select r from CommentReport r where r.reportReason = :reason", CommentReport.class);
        query.setParameter("reason",reason);
        return query.getResultList();
    }

    @Override
    public Optional<CommentReport> findCommentReport(Long id) {
        return Optional.ofNullable(em.find(CommentReport.class,id));
    }

    @Override
    public Optional<ReviewReport> findReviewReport(Long id) {
        return Optional.ofNullable(em.find(ReviewReport.class,id));

    }


}
