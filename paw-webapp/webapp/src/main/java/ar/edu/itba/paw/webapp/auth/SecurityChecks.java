package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.controller.ReviewController;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    public SecurityChecks(UserService us, ContentService cs) {
        this.us = us;
        this.cs = cs;
    }

    public boolean canReview(Long userId, Long contentId) {
        LOGGER.info("canReview /{}: Called", userId);
        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        final User userById = us.findById(userId).orElseThrow(UserNotFoundException::new);
        final User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        if(userById == loggedUser && Objects.equals(userById.getRole(), "admin")) {
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
}
