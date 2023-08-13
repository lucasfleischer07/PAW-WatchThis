package ar.edu.itba.paw.webapp.controller.queryParams;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.ReportReason;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.CommentService;
import ar.edu.itba.paw.services.ReportService;
import ar.edu.itba.paw.services.ReviewService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.QueryParam;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

public class GetCommentsParams {
    public static boolean checkUser(final Long userId,
                                    UserService us) {
        if(userId != null) {
            User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
            if(userId == user.getId()) {
                return true;
            } else {
                throw new ForbiddenException("Current logged user does not match");
            }
        } else {
            throw new InvalidParameterException("User id is null");
        }
    }

    public static boolean isAdmin(Long userId, UserService us) {
        User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        return checkUser(userId, us) && Objects.equals(user.getRole(), "admin");
    }
}
