package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.dto.request.NewCommentDto;
import ar.edu.itba.paw.webapp.dto.response.CommentDto;
import ar.edu.itba.paw.webapp.exceptions.ForbiddenException;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Optional;

@Path("comment")
@Component
public class CommentController {
    @Autowired
    private ReviewService rs;
    @Autowired
    private UserService us;
    @Autowired
    private CommentService ccs;
    @Autowired
    private ReportService rrs;
    @Context
    UriInfo uriInfo;
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    
    // * ---------------------------------------------Comment GET ------------------------------------------------------
    //Endpoint para pedir los comentarios de una review
    @GET
    @Path("/{reviewId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getReviewComments(@PathParam("reviewId") final long reviewId,
                                      @QueryParam("pageNumber") @DefaultValue("1") int pageNum,
                                      @QueryParam("pageSize") @DefaultValue("10") int pageSize) {

        List<Comment> commentList = ccs.getReviewComments(reviewId,1);
        LOGGER.info("GET /{}: Comments geted from review with id {}", uriInfo.getPath(), reviewId);
        return Response.ok(CommentDto.mapCommentToCommentDto(uriInfo, commentList)).build();
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Comment POST------------------------------------------------------
    // Endpoint para crear un comentario
//    @POST
//    @Produces(value = {MediaType.APPLICATION_JSON})
//    @Path("/{reviewId}/add")
//    public Response commentReviewAdd(@PathParam("reviewId")final long reviewId, @Valid NewCommentDto commentDto) {
//
//        Optional<Review> review = rs.getReview(reviewId);
//        Optional<User> user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
//
//        if(!review.isPresent() || !user.isPresent()) {
//            throw new PageNotFoundException();
//        }
//
//        if(commentDto == null) {
////            TODO: Ver que error tirar aca
//            throw new PageNotFoundException();
//        }

//        TODO: hacer que este metodo devuelva el comment
//        Comment newComment = ccs.addComment(review.get(), user.get(), commentDto.getComment());

//        LOGGER.info("POST /{}: Comment created with id {}", uriInfo.getPath(), newComment.getCommentId());
//        return Response.created(CommentDto.getCommentUriBuilder(newComment, uriInfo).build()).build();
//        return null;
//    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Comments DELETE---------------------------------------------------
    // Endpoint para borrar un comment
    @DELETE
    @Path("/{commentId}/delete")
    public Response commentReviewDelete(@PathParam("commentId") final long commentId) {

        Optional<User> user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Optional<Comment> deleteComment = ccs.getComment(commentId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!deleteComment.isPresent()) {
            throw new PageNotFoundException();
        }

        if(user.get().getUserName().equals(deleteComment.get().getUser().getUserName())) {
            ccs.deleteComment(deleteComment.get());
        } else if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            rrs.delete(deleteComment.get(), deleteComment.get().getReports());
        } else {
            LOGGER.warn("Not allowed to delete",new ForbiddenException());
            throw new ForbiddenException();
        }

        LOGGER.info("DELETE /{}: Comment {} deleted successfully", uriInfo.getPath(), commentId);
        return Response.noContent().build();

    }

    // * ---------------------------------------------------------------------------------------------------------------

}
