package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.controller.queryParams.GetCommentsParams;
import ar.edu.itba.paw.webapp.dto.request.NewReportCommentDto;
import ar.edu.itba.paw.webapp.dto.response.CommentReportDto;
import ar.edu.itba.paw.webapp.exceptions.CommentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.dto.request.NewCommentDto;
import ar.edu.itba.paw.webapp.dto.response.CommentDto;
import ar.edu.itba.paw.webapp.utilities.ResponseBuildingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Path("comments")
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
    private static final int CONTENT_AMOUNT = 3;
    private static final int REPORTS_AMOUNT = 10;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getComments(@QueryParam("reviewId") final Long reviewId,
                                @QueryParam("reportedById") final Long reportedById) {
        LOGGER.info("GET /{}: Called", uriInfo.getPath());

        List<Comment> comments = GetCommentsParams.getCommentsByParams(reportedById, reviewId, us, ccs, rs);
        if(comments == null) {
            throw new ReviewNotFoundException();
        }

        Collection<CommentDto> commentListDto = CommentDto.mapCommentToCommentDto(uriInfo, comments);
        LOGGER.info("GET /{}: Comments got from review with id {}", uriInfo.getPath(), reviewId);
        return Response.ok(new GenericEntity<Collection<CommentDto>>(commentListDto){}).build();
    }

    @GET
    @Path("/{commentId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getComment(@PathParam("commentId") final Long commentId) {
        LOGGER.info("GET /{}: Called", uriInfo.getPath());
        Comment comment = ccs.getComment(commentId).orElseThrow(CommentNotFoundException::new);
        CommentDto commentDto = new CommentDto(uriInfo, comment);
        LOGGER.info("GET /{}: Comments got with id {}", uriInfo.getPath(), commentId);
        return Response.ok(commentDto).build();
    }


    // * ---------------------------------------------Comment GET ------------------------------------------------------
    //Endpoint para pedir los comentarios de una review
//    @GET
//    @Path("/{reviewId}")
//    @Produces(value = {MediaType.APPLICATION_JSON})
//    public Response getReviewComments(@PathParam("reviewId") final long reviewId) {
//
//        LOGGER.info("GET /{}: Called", uriInfo.getPath());
//        Review review = rs.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
//        List<Comment> comments = ccs.getReviewComments(review);
//        Collection<CommentDto> commentListDto = CommentDto.mapCommentToCommentDto(uriInfo, comments);
//        LOGGER.info("GET /{}: Comments got from review with id {}", uriInfo.getPath(), reviewId);
//        return Response.ok(new GenericEntity<Collection<CommentDto>>(commentListDto){}).build();
//    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Comment POST------------------------------------------------------
    // Endpoint para crear un comentario
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    @PreAuthorize("@securityChecks.checkUser(#userId)")
    public Response commentReviewAdd(@QueryParam("userId") final Long userId,
                                     @QueryParam("reviewId") final Long reviewId,
                                     @Valid NewCommentDto commentDto) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());
        if(commentDto == null || reviewId == null)
            throw new BadRequestException();

        final Review review = rs.getReview(reviewId).orElseThrow(ReviewNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        Comment newComment = ccs.addComment(review, user, commentDto.getComment());

        LOGGER.info("POST /{}: Comment created with id {}", uriInfo.getPath(), newComment.getCommentId());
        return Response.created(CommentDto.getCommentUriBuilder(newComment, uriInfo).build()).build();
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Comments DELETE---------------------------------------------------
    // Endpoint para borrar un comment
    @DELETE
    @Path("/{commentId}")
    @PreAuthorize("@securityChecks.canDeleteComment(#userId, #commentId)")
    public Response commentReviewDelete(@QueryParam("userId") final Long userId,
                                        @PathParam("commentId") final long commentId) {
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());

        Optional<User> user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Optional<Comment> deleteComment = ccs.getComment(commentId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!deleteComment.isPresent()) {
            throw new CommentNotFoundException();
        }

        if(user.get().getUserName().equals(deleteComment.get().getUser().getUserName())) {
            ccs.deleteComment(deleteComment.get());
        }
        else if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            rrs.delete(deleteComment.get(), deleteComment.get().getReports());
        } else {
            LOGGER.warn("DELETE /{}: Not allowed to delete",uriInfo.getPath(), new ForbiddenException());
            throw new ForbiddenException();
        }

        LOGGER.info("DELETE /{}: Comment {} deleted successfully", uriInfo.getPath(), commentId);
        return Response.noContent().build();

    }

    // * ---------------------------------------------------------------------------------------------------------------

    // * ---------------------------------------------Comments Reports---------------------------------------------------
    @GET
    @Path("/reports")
    @PreAuthorize("@securityChecks.isAdmin(#userId)")
    public Response getCommentReport(@QueryParam("userId") final Long userId,
                                     @QueryParam("page")@DefaultValue("1")final int page,
                                     @QueryParam(value = "reason") @DefaultValue("") ReportReason reason) {
        PageWrapper<CommentReport> commentsReported = rrs.getReportedComments(reason,page,REPORTS_AMOUNT);
        Collection<CommentReportDto> commentReportedListDto = CommentReportDto.mapCommentReportToCommentReportDto(uriInfo, commentsReported.getPageContent());
        LOGGER.info("GET /{}: Reported comments list success for admin user", uriInfo.getPath());
        Response.ResponseBuilder response = Response.ok(new GenericEntity<Collection<CommentReportDto>>(commentReportedListDto){});
        response.header("Total-Review-Reports",rrs.getReportedReviewsAmount(reason));
        response.header("Total-Comment-Reports",rrs.getReportedCommentsAmount(reason));
        ResponseBuildingUtils.setPaginationLinks(response,commentsReported , uriInfo);
        return response.build();
    }

    @POST
    @Path("/{commentId}/reports")
    @PreAuthorize("@securityChecks.checkUser(#userId)")
    public Response addCommentReport(@QueryParam("userId") final Long userId,
                                     @PathParam("commentId") long commentId,
                                     @Valid NewReportCommentDto commentReportDto) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());

        if(commentReportDto==null) {
            throw new BadRequestException("Must include report data");
        }

        final Comment comment = ccs.getComment(commentId).orElseThrow(CommentNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);
        rrs.addReport(comment, user, commentReportDto.getReportType());

        LOGGER.info("POST /{}: Comment {} reported", uriInfo.getPath(), comment.getCommentId());
        return Response.ok().build();
    }

    @DELETE
    @Path("/{commentId}/reports")
    @PreAuthorize("@securityChecks.isAdmin(#userId)")
    public Response deleteReport(@QueryParam("userId") final Long userId,
                                 @PathParam("commentId") long commentId) {
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());
        rrs.removeReports("comment", commentId);
        LOGGER.info("DELETE /{}: {} on contentId {} report deleted", uriInfo.getPath(), "Comment", commentId);
        return Response.noContent().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------

}
