package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.CommentNotFoundException;
import ar.edu.itba.paw.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.dto.request.NewReportCommentDto;
import ar.edu.itba.paw.webapp.dto.response.CommentReportDto;
import ar.edu.itba.paw.webapp.dto.response.ContentDto;
import ar.edu.itba.paw.webapp.dto.response.ReviewReportDto;
import ar.edu.itba.paw.webapp.utilities.ResponseBuildingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Path("reports")
@Component
public class ReportsController {
    @Autowired
    private ReviewService rs;
    @Autowired
    private UserService us;
    @Autowired
    private ReportService rrs;
    @Autowired
    private CommentService ccs;
    @Context
    private UriInfo uriInfo;

    private static final int REPORTS_AMOUNT = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportsController.class);


    // * ----------------------------------- Report Page ---------------------------------------------------------------
    @GET
    @Path("/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReportsByType(@PathParam("type") final String type,
                                     @QueryParam("page")@DefaultValue("1")final int page,
                                     @RequestParam(value = "reason",required = false)ReportReason reason) {
        LOGGER.info("GET /{}: Called", uriInfo.getPath());

        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);

        if(!Objects.equals(user.getRole(), "admin")) {
            LOGGER.warn("GET /{}: Login user {} not an admin", uriInfo.getPath(), user.getId());
            throw new ForbiddenException();
        }

        if(Objects.equals(type, "reviews")) {
            PageWrapper<ReviewReport> reviewsReported = rrs.getReportedReviews(reason,page,REPORTS_AMOUNT);
            Collection<ReviewReportDto> reviewsReportedListDto = ReviewReportDto.mapReviewReportToReviewReportDto(uriInfo, reviewsReported.getPageContent());
            LOGGER.info("GET /{}: Reported reviews list success for admin user {}", uriInfo.getPath(), user.getId());
            Response.ResponseBuilder response = Response.ok(new GenericEntity<Collection<ReviewReportDto>>(reviewsReportedListDto){});
            ResponseBuildingUtils.setPaginationLinks(response,reviewsReported , uriInfo);
            return response.build();
        } else if(Objects.equals(type, "comments")) {
            PageWrapper<CommentReport> commentsReported = rrs.getReportedComments(reason,page,REPORTS_AMOUNT);
            Collection<CommentReportDto> commentReportedListDto = CommentReportDto.mapCommentReportToCommentReportDto(uriInfo, commentsReported.getPageContent());
            LOGGER.info("GET /{}: Reported comments list success for admin user {}", uriInfo.getPath(), user.getId());
            Response.ResponseBuilder response = Response.ok(new GenericEntity<Collection<CommentReportDto>>(commentReportedListDto){});
            ResponseBuildingUtils.setPaginationLinks(response,commentsReported , uriInfo);
            return response.build();
        } else {
            throw new ContentNotFoundException();
        }
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Review Report ------------------------------------------------------------
    @POST
    @Path("/review/{reviewId}")
    public Response addReviewReport(@PathParam("reviewId") long reviewId,
                                    @Valid NewReportCommentDto commentReportDto) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());
        final Review review = rs.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);
        rrs.addReport(review, user, commentReportDto.getReportType());

        LOGGER.info("POST /{}: Review {} reported", uriInfo.getPath(), review.getId());
        return Response.ok().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Comment Report ------------------------------------------------------------
    @POST
    @Path("/comment/{commentId}")
    public Response addCommentReport(@PathParam("commentId") long commentId,
                                     @Valid NewReportCommentDto commentReportDto) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());

        final Comment comment = ccs.getComment(commentId).orElseThrow(CommentNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);
        rrs.addReport(comment, user, commentReportDto.getReportType());

        LOGGER.info("POST /{}: Comment {} reported", uriInfo.getPath(), comment.getCommentId());
        return Response.ok().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Delete Report From Reported List-------------------------------------------
    @DELETE
    @Path("/deleteReport/{type}/{commentOrReviewId}")
    public Response deleteReport(@PathParam("commentOrReviewId") long commentOrReviewId,
                                 @PathParam("type") String type) {
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());

        rrs.removeReports(type, commentOrReviewId);
        LOGGER.info("DELETE /{}: {} on contentId {} report deleted", uriInfo.getPath(), type, commentOrReviewId);
        return Response.noContent().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------
}