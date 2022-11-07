package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class CommentServiceImpl implements CommentService{
    private final CommentDao commentDao;
    @Autowired
    public CommentServiceImpl(final CommentDao commentDao){this.commentDao=commentDao;}
    @Override
    public void addComment(Review review, User user, String text) {
        commentDao.addComment(review,user,text);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentDao.deleteComment(comment);
    }

    @Override
    public Optional<Comment> getComment(Long id) {
        return commentDao.getComment(id);
    }
}
