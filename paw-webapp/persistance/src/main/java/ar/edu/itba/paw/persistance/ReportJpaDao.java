package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
            em.remove(review);
        } else if(reviewOrComment instanceof Comment){
            Comment comment=(Comment) reviewOrComment;
            em.remove(comment);

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

        } else if(reviewOrComment instanceof Comment){
            Comment comment=(Comment) reviewOrComment;
            for (CommentReport report:comment.getReports()
                 ) {
                em.remove(report);
            }

        }
        else throw new IllegalArgumentException();
    }

    @Override
    public void addReport(Object reviewOrComment,User user, ReportReason reason) {
        if(reviewOrComment instanceof Review){
            Review review=(Review) reviewOrComment;
            ReviewReport reviewReport=new ReviewReport(user,review,reason);
            em.persist(reviewReport);
            review.getReports().add(reviewReport);

        } else if(reviewOrComment instanceof Comment){
            Comment comment=(Comment) reviewOrComment;
            CommentReport commentReport=new CommentReport(user,comment,reason);
            em.persist(commentReport);
            comment.getReports().add(commentReport);
        }
        else throw new IllegalArgumentException();
    }

    @Override
    public Long getReportedContentId(Object reviewOrComment) {
        Query query;
        if(reviewOrComment instanceof Review){
            Review review = (Review) reviewOrComment;
            query=em.createNativeQuery("select r.id from ReviewReport r where :reviewId = r.reviewid ");
            query.setParameter("reviewId",review.getId());

        } else if(reviewOrComment instanceof Comment){
            Comment comment=(Comment) reviewOrComment;
            query=em.createNativeQuery("select c.id from CommentReport c where :commentId = c.commentid ");
            query.setParameter("commentId",comment.getCommentId());
        }
        else throw new IllegalArgumentException();

        return (Long) query.getSingleResult();
    }

    @Override
    public PageWrapper<ReviewReport> getReportedReviews(int page,int pageSize) {
        TypedQuery<ReviewReport> query=em.createQuery("select r from  ReviewReport r where r.id in(select min(r2.id) from ReviewReport r2 group by r2.review) order by r.id asc",ReviewReport.class);
        TypedQuery<ReviewReport> countQuery=em.createQuery("select r from  ReviewReport r where r.id in(select min(r2.id) from ReviewReport r2 group by r2.review) order by r.id asc",ReviewReport.class);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);


        long totalContent = PageWrapper.calculatePageAmount(countQuery.getResultList().size(),pageSize);

        return new PageWrapper<ReviewReport>(page,totalContent,pageSize,query.getResultList(),countQuery.getResultList().size());

    }

    @Override
    public ReviewReport getReportedReview(Long reportId){
        TypedQuery<ReviewReport> query =em.createQuery("select r from ReviewReport r where :reportId = r.id ",ReviewReport.class);
        query.setParameter("reportId",reportId);
        return query.getSingleResult();
    }

    @Override
    public PageWrapper<ReviewReport> getReportedReviewsByReason(ReportReason reason,int page,int pageSize){
        TypedQuery<ReviewReport>query= em.createQuery("select r from ReviewReport r where  r.id in(select min(r2.id) from ReviewReport r2 where r2.reportReason = :reason group by r2.review) order by r.id asc", ReviewReport.class);
        TypedQuery<ReviewReport>countQuery= em.createQuery("select r from ReviewReport r where  r.id in(select min(r2.id) from ReviewReport r2 where r2.reportReason = :reason group by r2.review) order by r.id asc", ReviewReport.class);
        countQuery.setParameter("reason",reason);
        query.setParameter("reason",reason);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        long totalContent = PageWrapper.calculatePageAmount(countQuery.getResultList().size(),pageSize);

        return new PageWrapper<ReviewReport>(page,totalContent,pageSize,query.getResultList(),countQuery.getResultList().size());
    }

    @Override
    public int getReportedReviewsAmount(){
        TypedQuery<ReviewReport> countQuery=em.createQuery("select r from  ReviewReport r where r.id in(select min(r2.id) from ReviewReport r2 group by r2.review) order by r.id asc",ReviewReport.class);
        return countQuery.getResultList().size();
    }

    @Override
    public int getReportedReviewsAmountByReason(ReportReason reason){
        TypedQuery<ReviewReport>countQuery= em.createQuery("select r from ReviewReport r where  r.id in(select min(r2.id) from ReviewReport r2 where r2.reportReason = :reason group by r2.review) order by r.id asc", ReviewReport.class);
        countQuery.setParameter("reason",reason);
        return countQuery.getResultList().size();
    }

    @Override
    public PageWrapper<CommentReport> getReportedComments(int page,int pageSize) {
        TypedQuery<CommentReport>query= em.createQuery("select r from CommentReport r where r.id in(select min(r2.id) from CommentReport r2 group by r2.comment) order by r.id asc", CommentReport.class);
        TypedQuery<CommentReport>countQuery= em.createQuery("select r from CommentReport r where r.id in(select min(r2.id) from CommentReport r2 group by r2.comment) order by r.id asc", CommentReport.class);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        long totalContent = PageWrapper.calculatePageAmount(countQuery.getResultList().size(),pageSize);

        return new PageWrapper<CommentReport>(page,totalContent,pageSize,query.getResultList(),countQuery.getResultList().size());
    }

    @Override
    public CommentReport getReportedComment(Long reportId){
        TypedQuery<CommentReport> query =em.createQuery("select c from CommentReport c where :reportId = c.id ",CommentReport.class);
        query.setParameter("reportId",reportId);
        return query.getSingleResult();
    }

    @Override
    public PageWrapper<CommentReport> getReportedCommentsByReason(ReportReason reason,int page,int pageSize){
        TypedQuery<CommentReport>query= em.createQuery("select r from CommentReport r  where r.id in(select min(r2.id) from CommentReport r2 where r2.reportReason = :reason group by r2.comment) order by r.id asc", CommentReport.class);
        TypedQuery<CommentReport>countQuery= em.createQuery("select r from CommentReport r  where r.id in(select min(r2.id) from CommentReport r2 where r2.reportReason = :reason group by r2.comment) order by r.id asc", CommentReport.class);
        countQuery.setParameter("reason",reason);

        query.setParameter("reason",reason);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        long totalContent = PageWrapper.calculatePageAmount(countQuery.getResultList().size(),pageSize);

        return new PageWrapper<CommentReport>(page,totalContent,pageSize,query.getResultList(),countQuery.getResultList().size());
    }

    @Override
    public Optional<CommentReport> findCommentReport(Long id) {
        return Optional.ofNullable(em.find(CommentReport.class,id));
    }

    @Override
    public Optional<ReviewReport> findReviewReport(Long id) {
        return Optional.ofNullable(em.find(ReviewReport.class,id));

    }

    @Override
    public int getReportedCommentsAmount(){
        TypedQuery<CommentReport>countQuery= em.createQuery("select r from CommentReport r where r.id in(select min(r2.id) from CommentReport r2 group by r2.comment) order by r.id asc", CommentReport.class);
        return countQuery.getResultList().size();
    }

    @Override
    public int getReportedCommentsAmountByReason(ReportReason reason){
        TypedQuery<CommentReport>countQuery= em.createQuery("select r from CommentReport r  where r.id in(select min(r2.id) from CommentReport r2 where r2.reportReason = :reason group by r2.comment) order by r.id asc", CommentReport.class);
        countQuery.setParameter("reason",reason);
        return countQuery.getResultList().size();
    }


}
