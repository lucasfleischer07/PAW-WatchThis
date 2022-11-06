package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

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
            em.merge(content);
        } else if(reviewOrComment instanceof Comment){
            Comment comment=(Comment) reviewOrComment;
            Review review=comment.getReview();
            review.getComments().remove(comment);
            em.merge(review);
        }
        else throw new IllegalArgumentException();
    }

    @Override
    public void removeReports(Object reviewOrComment) {
        if(reviewOrComment instanceof Review){
            Review review=(Review) reviewOrComment;
            review.getReports().removeAll(review.getReports());
            em.merge(review);
        } else if(reviewOrComment instanceof Comment){
            Comment comment=(Comment) reviewOrComment;
            comment.getReports().removeAll(comment.getReports());
            em.merge(comment);
        }
        else throw new IllegalArgumentException();
    }

    @Override
    public void addReport(Object reviewOrComment, ReportReason reason, String text) {
        if(reviewOrComment instanceof Review){
            Review review=(Review) reviewOrComment;
            Report toAdd =new Report(review.getCreator(),review,text,reason);
            em.persist(toAdd);
            em.merge(review);
        } else if(reviewOrComment instanceof Comment){
            Comment comment=(Comment) reviewOrComment;
            Report toAdd =new Report(comment.getUser(),comment,text,reason);
            em.persist(toAdd);
            em.merge(comment);
        }
        else throw new IllegalArgumentException();
    }

    @Override
    public List<Report> getReportedReviews() {
        TypedQuery<Report>query= em.createQuery("select r from Report r  where r.review <> :null",Report.class);
        query.setParameter("null",null);
        return query.getResultList();
    }

    @Override
    public List<Report> getReportedComments() {
        TypedQuery<Report>query= em.createQuery("select r from Report r where r.comment <> :null",Report.class);
        query.setParameter("null",null);
        return query.getResultList();
    }


}
