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
import java.util.Optional;

@Primary
@Repository
@Transactional
public class CommentJpaDao implements CommentDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public void addComment(Review review, User user, String text) {
        em.persist(new Comment(user,review,text));

    }

    @Override
    public void deleteComment(Comment comment) {
        em.remove(comment);

    }

    @Override
    public Optional<Comment> getComment(Long id) {
        return Optional.ofNullable(em.find(Comment.class,id));

    }
}
