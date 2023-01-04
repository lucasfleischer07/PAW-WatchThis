package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    void addComment(Review review, User user, String text);
    void deleteComment(Comment comment);
    Optional<Comment> getComment(Long id);
    List<Comment> getReviewComments(Long reviewId,int page);
}
