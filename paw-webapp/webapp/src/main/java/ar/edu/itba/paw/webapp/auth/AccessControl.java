package ar.edu.itba.paw.webapp.auth;


import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.CommentService;
import ar.edu.itba.paw.services.ReviewService;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Ignore "Method is never used"
 */
@Component
public class AccessControl {

    @Autowired
    private PawUserDetailsService userDetailsService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    @Transactional(readOnly = true)
    public boolean checkUser(HttpServletRequest request, Long id) {
        final UserDetails userDetails = getUserDetailsFromSecurityContext();
        if (userDetails == null) {
            return false;
        }
        final User user=userService.findById(id).orElse(null);
        if(user==null){
            return true;
        }
        return userDetails.getUsername().equals(user.getEmail());
    }
    @Transactional(readOnly = true)
    public boolean checkReviewOwnerOrAdmin(HttpServletRequest request, Long reviewId) {
        final UserDetails userDetails = getUserDetailsFromSecurityContext();
        if (userDetails == null) {
            return false;
        }
        if(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            return true;
        }
        final Review review = reviewService.getReview(reviewId).orElse(null);
        if (review == null) {
            return true; // Jersey will throw 404 Response
        }
        return userDetails.getUsername().equals(review.getUser().getEmail());
    }
    @Transactional(readOnly = true)
    public boolean checkReviewNotOwner(HttpServletRequest request, Long reviewId) {
        final UserDetails userDetails = getUserDetailsFromSecurityContext();
        if (userDetails == null) {
            return false;
        }
        final Review review = reviewService.getReview(reviewId).orElse(null);
        if (review == null) {
            return true; // Jersey will throw 404 Response
        }
        return !userDetails.getUsername().equals(review.getUser().getEmail());
    }
    @Transactional(readOnly = true)
    public boolean checkCommentOwnerOrAdmin(HttpServletRequest request, Long commentId) {
        final UserDetails userDetails = getUserDetailsFromSecurityContext();
        if (userDetails == null) {
            return false;
        }
        if(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            return true;
        }
        final Comment comment = commentService.getComment(commentId).orElse(null);
        if (comment == null) {
            return true; // Jersey will throw 404 Response
        }
        return userDetails.getUsername().equals(comment.getUser().getEmail());
    }

    @Transactional(readOnly = true)
    public boolean checkCommentNotOwner(HttpServletRequest request, Long commentId) {
        final UserDetails userDetails = getUserDetailsFromSecurityContext();
        if (userDetails == null) {
            return false;
        }
        final Comment comment = commentService.getComment(commentId).orElse(null);
        if (comment == null) {
            return true; // Jersey will throw 404 Response
        }
        return !userDetails.getUsername().equals(comment.getUser().getEmail());
    }

    private UserDetails getUserDetailsFromSecurityContext() {
        UserDetails userDetails = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            userDetails = userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return userDetails;
    }
}
