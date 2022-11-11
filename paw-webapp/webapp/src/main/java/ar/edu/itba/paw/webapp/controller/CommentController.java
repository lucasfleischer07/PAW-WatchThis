package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.CommentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class CommentController {
    private final ReviewService rs;
    private final UserService us;
    private final CommentService ccs;
    private final ReportService rrs;

    @Autowired
    public CommentController(ReviewService rs, UserService us, CommentService ccs, ReportService rrs) {
        this.us = us;
        this.rs = rs;
        this.ccs = ccs;
        this.rrs = rrs;
    }


    // * ---------------------------------------------Comments add -----------------------------------------------------
    @RequestMapping(value = "/review/add/comment/{reviewId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView commentReview(Principal userDetails,
                                      @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
                                      final BindingResult errors,
                                      @PathVariable("reviewId")final long reviewId,
                                      HttpServletRequest request) {

        Optional<User> user = us.findByEmail(userDetails.getName());
        Optional<Review> review = rs.getReview(reviewId);

        if(!review.isPresent()) {
            throw new PageNotFoundException();
        }

        ccs.addComment(review.get(), user.get(), commentForm.getComment());

        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:"+ referer);
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Comments delete---------------------------------------------------
    @RequestMapping(value = "/comment/{commentId:[0-9]+}/delete", method = {RequestMethod.POST})
    public ModelAndView commentReviewDelete(Principal userDetails,
                                            @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
                                            @PathVariable("commentId")final long commentId,
                                            HttpServletRequest request) {

        Optional<User> user = us.findByEmail(userDetails.getName());
        Optional<Comment> deleteComment = ccs.getComment(commentId);
        if(!deleteComment.isPresent()) {
            throw new PageNotFoundException();
        }


        if(user.get().getUserName().equals(deleteComment.get().getUser().getUserName())) {
            ccs.deleteComment(deleteComment.get());
        } else {
            rrs.delete(deleteComment, deleteComment.get().getReports());
        }

        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:"+ referer);

    }

    // * ---------------------------------------------------------------------------------------------------------------
}
