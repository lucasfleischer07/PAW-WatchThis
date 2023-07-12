package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Primary
@Repository
@Transactional
public class CommentJpaDao implements CommentDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment addComment(Review review, User user, String text) {
        Comment comment = new Comment(user,review,text);
        em.persist(comment);
        em.flush();
        return comment;
    }

    @Override
    public void deleteComment(Comment comment) {
        em.remove(comment);

    }

    @Override
    public Optional<Comment> getComment(Long id) {
        return Optional.ofNullable(em.find(Comment.class,id));

    }

    @Override
    public Set<Comment> getReviewComment(Long reviewId){
        Optional<Review> maybeReview= Optional.ofNullable(em.find(Review.class,reviewId));
        return maybeReview.isPresent()?maybeReview.get().getComments():null;
    }
}
