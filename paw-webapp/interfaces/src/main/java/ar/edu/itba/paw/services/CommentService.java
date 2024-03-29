package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.PageWrapper;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CommentService {
    Comment addComment(Review review, User user, String text);
    void deleteComment(Comment comment);
    Optional<Comment> getComment(Long id);
    Set<Comment> getReviewComments(Long reviewId);
    List<Comment> getReviewComments(Review review);
}
