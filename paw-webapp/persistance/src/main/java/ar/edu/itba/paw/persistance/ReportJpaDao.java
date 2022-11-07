package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

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
            content.getContentReviews().remove(review);
            em.remove(review);
            em.merge(content);
        } else if(reviewOrComment instanceof Comment){
            Comment comment=(Comment) reviewOrComment;
            Review review=comment.getReview();
            review.getComments().remove(comment);
            em.remove(comment);
            em.merge(review);
            em.merge(review.getContent());
        }
        else throw new IllegalArgumentException();
    }

    @Override
    public void removeReports(Object reviewOrComment) {
        if(reviewOrComment instanceof Review){
            Review review=(Review) reviewOrComment;
            review.getReports().removeAll(review.getReports());
            em.merge(review);
            em.merge(review.getContent());
        } else if(reviewOrComment instanceof Comment){
            Comment comment=(Comment) reviewOrComment;
            comment.getReports().removeAll(comment.getReports());
            em.merge(comment);
            em.merge(comment.getReview());
            em.merge(comment.getReview().getContent());
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
            em.merge(review.getContent());
        } else if(reviewOrComment instanceof Comment){
            Comment comment=(Comment) reviewOrComment;
            CommentReport toAdd =new CommentReport(user,comment,reason);
            em.persist(toAdd);
            em.merge(comment);
            em.merge(comment.getReview());
            em.merge(comment.getReview().getContent());
        }
        else throw new IllegalArgumentException();
    }

    @Override
    public List<ReviewReport> getReportedReviews() {
        TypedQuery<ReviewReport> query=em.createQuery("select r from ReviewReport r",ReviewReport.class);
        return query.getResultList();

    }

    @Override
    public List<CommentReport> getReportedComments() {
        TypedQuery<CommentReport>query= em.createQuery("select r from CommentReport r", CommentReport.class);
        return query.getResultList();
    }


}
