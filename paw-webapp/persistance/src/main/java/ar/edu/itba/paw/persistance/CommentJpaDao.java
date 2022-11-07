package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Optional;

@Primary
@Repository
@Transactional
public class CommentJpaDao implements CommentDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public void addComment(Review review, User user, String text) {
        Comment toAdd=new Comment(user,review,text, LocalDateTime.now());
        em.persist(toAdd);
        em.merge(review);
        em.merge(review.getContent());
        em.merge(user);
    }

    @Override
    public void deleteComment(Comment comment) {
        User user=comment.getUser();
        Review review=comment.getReview();
        em.remove(comment);
        em.merge(user);
        em.merge(review);
        em.merge(review.getContent());
    }

    @Override
    public Optional<Comment> getComment(Long id) {

        TypedQuery<Comment> query=em.createQuery("select c from Comment c where c.commentId =:id",Comment.class);
        query.setParameter("id",id);
        return Optional.ofNullable(query.getSingleResult());
    }
}
