package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.CommentService;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.controller.ReviewController;
import ar.edu.itba.paw.webapp.exceptions.CommentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

@Component
public class SecurityChecks {
    private final UserService us;
    private final ContentService cs;
    private final CommentService ccs;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    public SecurityChecks(UserService us, ContentService cs, CommentService ccs) {
        this.us = us;
        this.cs = cs;
        this.ccs = ccs;
    }

    public boolean checkUser(Long userId) {
        LOGGER.info("checkUser userId = {}: Called", userId);
        final User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        if(userId == loggedUser.getId()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isAdmin(Long userId) {
        LOGGER.info("isAdmin userId = {}: Called", userId);
        final User userById = us.findById(userId).orElseThrow(UserNotFoundException::new);
        return checkUser(userId) && Objects.equals(userById.getRole(), "admin");
    }

    public boolean canReview(Long userId, Long contentId) {
        LOGGER.info("canReview userId = {}: Called", userId);
        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        final User userById = us.findById(userId).orElseThrow(UserNotFoundException::new);
        final User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        if(userId == loggedUser.getId() && Objects.equals(userById.getRole(), "admin")) {
            final List<User> userList = cs.getContentReviewers(contentId);
            for (User user2 : userList) {
                if (user2.getId() == loggedUser.getId()) {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }

    public boolean canDeleteComment(Long userId, Long commentId) {
        LOGGER.info("canDeleteComment userId = {}: Called", userId);
        final Comment comment = ccs.getComment(commentId).orElseThrow(CommentNotFoundException::new);
        if(isAdmin(userId) || comment.getUser().getId() == userId) {
            return true;
        } else {
            return false;
        }
    }

}
