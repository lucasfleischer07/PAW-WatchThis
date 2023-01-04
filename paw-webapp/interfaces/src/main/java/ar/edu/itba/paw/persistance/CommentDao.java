package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    void addComment(Review review, User user, String text);
    void deleteComment(Comment comment);
    Optional<Comment> getComment(Long id);
    List<Comment> getReviewComment(Long reviewId, int page);
}
