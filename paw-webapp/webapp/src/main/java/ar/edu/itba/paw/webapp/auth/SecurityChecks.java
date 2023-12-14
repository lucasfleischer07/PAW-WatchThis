package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.CommentService;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.ReviewService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.controller.ReviewController;
import ar.edu.itba.paw.webapp.exceptions.CommentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

@Component
public class SecurityChecks {
    private final UserService us;
    private final ContentService cs;
    private final ReviewService rs;
    private final CommentService ccs;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    public SecurityChecks(UserService us, ContentService cs, ReviewService rs, CommentService ccs) {
        this.us = us;
        this.cs = cs;
        this.rs = rs;
        this.ccs = ccs;
    }

    public boolean checkUser(Long userId) {
        LOGGER.info("checkUser userId = {}: Called", userId);
        final User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        if(userId == loggedUser.getId()) {
            return true;
        } else {
            throw new ForbiddenException("Users dont match");
        }
    }

    public boolean checkUserLists() {
        final User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        LOGGER.info("checkUser userId = {}: Called", loggedUser);
        return true;
    }

    public boolean isAdmin(Long userId) {
        LOGGER.info("isAdmin userId = {}: Called", userId);
        final User userById = us.findById(userId).orElseThrow(UserNotFoundException::new);
        if(checkUser(userId) && Objects.equals(userById.getRole(), "admin")) {
            return true;
        } else {
            throw new ForbiddenException("User must be admin");
        }
    }

    public boolean canReview(Long userId, Long contentId, String type) {
        LOGGER.info("canReview userId = {}: Called", userId);
        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        if(!Objects.equals(type, "serie") && !Objects.equals(type, "movie")) {
            throw new InvalidParameterException("Type parameter incorrect");
        }

        if(checkUser(userId)) {
            final List<User> userList = cs.getContentReviewers(contentId);
            for (User user2 : userList) {
                if (user2.getId() == userId) {
                    throw new ForbiddenException("User already review this content");
                }
            }
        }

        return true;
    }

    public boolean canDeleteReview(Long reviewId) {
        LOGGER.info("canDeleteReview: Called");
        final User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        final Review review = rs.getReview(reviewId).orElseThrow(ReviewNotFoundException::new);
        if ((checkUser(loggedUser.getId()) && Objects.equals(loggedUser.getRole(), "admin")) || (checkUser(loggedUser.getId()) && review.getUser().getId() == loggedUser.getId())) {
            return true;
        } else {
            throw new ForbiddenException("User must be admin or owner from the comment");
        }
    }

    public boolean canEditReview(Long userId, Long reviewId) {
        LOGGER.info("canEditReview userId = {}: Called", userId);
        final Review review = rs.getReview(reviewId).orElseThrow(ReviewNotFoundException::new);
        return checkUser(userId) && review.getUser().getId() == userId;
    }

    public boolean canDeleteComment(Long commentId) {
        final User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        LOGGER.info("canDeleteComment userId = {}: Called", loggedUser.getId());
        final Comment comment = ccs.getComment(commentId).orElseThrow(CommentNotFoundException::new);
        if ((checkUser(loggedUser.getId()) && Objects.equals(loggedUser.getRole(), "admin")) || (checkUser(loggedUser.getId()) && comment.getUser().getId() == loggedUser.getId())) {
            return true;
        } else {
            throw new ForbiddenException("User must be admin or owner from the review");
        }
    }
    public boolean checkReported(Long reportedById){
        if(reportedById == null) {
            return true;
        }
        final User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        if(reportedById == loggedUser.getId()) {
            return true;
        } else {
            throw new ForbiddenException("Users dont match");
        }
    }
}
