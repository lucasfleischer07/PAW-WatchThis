package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.exceptions.ForbiddenException;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.CommentForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.Optional;

@Path("comment")
@Component
public class CommentController {

    @Autowired
    private final ReviewService rs;
    @Autowired
    private final UserService us;
    @Autowired
    private final CommentService ccs;
    @Autowired
    private final ReportService rrs;
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);


    @Autowired
    public CommentController(ReviewService rs, UserService us, CommentService ccs, ReportService rrs) {
        this.us = us;
        this.rs = rs;
        this.ccs = ccs;
        this.rrs = rrs;
    }
    
    // * ---------------------------------------------Comment endpoints-------------------------------------------------
    //Endpoint para pedir los comentarios de una review
    //PARA RECIBIR UN FORM O UN JASON ---> @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED,})
    @GET
    @Path("/{reviewId}")
    public Response getReviewComments(@PathParam("reviewId") final long reviewid,
                                      @QueryParam("page")@DefaultValue("1")final int page){
        //cosas
        ccs.getReviewComments(reviewid,1);
        //lo cambiamos y lo retornamos
        return null;
    }

    @POST
    @Path("/{reviewId}")
    public Response commentReviewAdd(@PathParam("reviewId")final long reviewId) {

//        Optional<Review> review = rs.getReview(reviewId);
//        Optional<User> user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
//
//        if(!review.isPresent() || !user.isPresent()) {
//            throw new PageNotFoundException();
//        }
//
//        ccs.addComment(review.get(), user.get(), commentForm.getComment());
//
//        String referer = request.getHeader("Referer");
//        return new ModelAndView("redirect:"+ referer);
//    }

    // * ---------------------------------------------Comments add -----------------------------------------------------
//    @RequestMapping(value = "/review/add/comment/{reviewId:[0-9]+}", method = {RequestMethod.POST})
//    public ModelAndView commentReview(Principal userDetails,
//                                      @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
//                                      @PathVariable("reviewId")final long reviewId,
//                                      HttpServletRequest request) {
//
//        Optional<User> user = us.findByEmail(userDetails.getName());
//        Optional<Review> review = rs.getReview(reviewId);
//
//        if(!review.isPresent()) {
//            throw new PageNotFoundException();
//        }
//
//        ccs.addComment(review.get(), user.get(), commentForm.getComment());
//
//        String referer = request.getHeader("Referer");
//        return new ModelAndView("redirect:"+ referer);
//    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Comments delete---------------------------------------------------
//    @RequestMapping(value = "/comment/{commentId:[0-9]+}/delete", method = {RequestMethod.POST})
//    public ModelAndView commentReviewDelete(Principal userDetails,
//                                            @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
//                                            @PathVariable("commentId")final long commentId,
//                                            HttpServletRequest request) {
//
//        Optional<User> user = us.findByEmail(userDetails.getName());
//        Optional<Comment> deleteComment = ccs.getComment(commentId);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if(!deleteComment.isPresent()) {
//            throw new PageNotFoundException();
//        }
//
//        if(user.get().getUserName().equals(deleteComment.get().getUser().getUserName())) {
//            ccs.deleteComment(deleteComment.get());
//        } else if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//            rrs.delete(deleteComment.get(), deleteComment.get().getReports());
//        } else {
//            LOGGER.warn("Not allowed to delete",new ForbiddenException());
//            throw new ForbiddenException();
//        }
//
//        String referer = request.getHeader("Referer");
//        return new ModelAndView("redirect:"+ referer);
//
//    }

    // * ---------------------------------------------------------------------------------------------------------------
}
