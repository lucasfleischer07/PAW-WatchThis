package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.controller.queryParams.GetReviewsParams;
import ar.edu.itba.paw.webapp.dto.request.BasicRequestDto;
import ar.edu.itba.paw.webapp.dto.request.NewReportCommentDto;
import ar.edu.itba.paw.webapp.dto.response.ContentDto;
import ar.edu.itba.paw.webapp.dto.response.ReviewReportDto;
import ar.edu.itba.paw.webapp.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.dto.request.NewReviewDto;
import ar.edu.itba.paw.webapp.dto.response.ReviewDto;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.utilities.ResponseBuildingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Path("reviews")
@Component
public class ReviewController {
    @Autowired
    private ReviewService rs;
    @Autowired
    private ContentService cs;
    @Autowired
    private UserService us;
    @Autowired
    private ReportService rrs;
    @Context
    private UriInfo uriInfo;
    private static final int REPORTS_AMOUNT = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    // * ----------------------------------- Movies and Series Review Gets ---------------------------------------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("@securityChecks.checkReported(#reportedById)")
    public Response reviews(@QueryParam("contentId") final Long contentId,
                            @QueryParam("userId") final Long userId,
                            @QueryParam("reportedById") final Long reportedById,
                            @QueryParam("page") @DefaultValue("1") int page,
                            @QueryParam("likedById") final Long likeById,
                            @QueryParam("dislikedById") final Long dislikeById) {
        LOGGER.info("GET /{}: Called",uriInfo.getPath());
        PageWrapper<Review> reviewList = GetReviewsParams.getReviewsByParams(userId, contentId, reportedById, page, likeById, dislikeById, us, cs, rs);
        if(reviewList == null) {
            LOGGER.warn("GET /{}: Invalid page param",uriInfo.getPath());
            throw new ContentNotFoundException();
        }
        Collection<ReviewDto> reviewDtoList = ReviewDto.mapReviewToReviewDto(uriInfo, reviewList.getPageContent());

        LOGGER.info("GET /{}: Review list for content {}",uriInfo.getPath(), contentId);

        final Response.ResponseBuilder response = Response.ok(new GenericEntity<Collection<ReviewDto>>(reviewDtoList){});
        ResponseBuildingUtils.setPaginationLinks(response,reviewList , uriInfo);
        response.header("Total-User-Review", reviewList.getElemsAmount());
        return response.build();
    }

    @GET
    @Path("/{reviewId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reviews(@PathParam("reviewId") final long reviewId) {
        LOGGER.info("GET /{}: Called",uriInfo.getPath());
        Review review = rs.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        ReviewDto reviewDto = new ReviewDto(uriInfo, review);
        LOGGER.info("GET /{}: Review {}",uriInfo.getPath(), reviewId);
        return Response.ok(reviewDto).build();
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Review Creation -----------------------------------------------------------
//    Endpoint para crear una resenia
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    @PreAuthorize("@securityChecks.canReview(#reviewDto.userId, #reviewDto.contentId, #reviewDto.type)")
    public Response reviews(@Valid NewReviewDto reviewDto) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());

        final Content content = cs.findById(reviewDto.getContentId()).get();
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        if(reviewDto == null){
            throw new BadRequestException("Must include review data");
        }

        try {
            rs.addReview(reviewDto.getName(), reviewDto.getDescription(), reviewDto.getRating(), reviewDto.getType(), user, content);
            LOGGER.info("POST /{}: Review added", uriInfo.getPath());

        } catch (DuplicateKeyException e) {
            LOGGER.warn("POST /{}: Duplicate review", uriInfo.getPath(), new BadRequestException());
        }

        return Response.created(ReviewDto.getReviewUriBuilder(content, uriInfo).build()).build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Review delete-----------------------------------------------------
//    Endpoint para eliminar una resenia
    @DELETE
    @Path("/{reviewId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @PreAuthorize("@securityChecks.canDeleteReview(#reviewId)")
    public Response deleteReview(@PathParam("reviewId") final Long reviewId) {
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());

        final Review review = rs.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);
        if(review.getUser().getUserName().equals(user.getUserName())) {
            rs.deleteReview(reviewId);
            LOGGER.info("DELETE /{}: Review Deleted by user owner", uriInfo.getPath());
            return Response.noContent().build();
        } else if(Objects.equals(user.getRole(), "admin")) {
            rrs.delete(review, null);
            LOGGER.info("DELETE /{}: Review Deleted by admin", uriInfo.getPath());
            return Response.noContent().build();
        } else {
            LOGGER.warn("DELETE /{}: Not allowed to delete", uriInfo.getPath());
            throw new ForbiddenException();
        }

    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Review edition----------------------------------------------------
//    Endpoint para editar una review
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{reviewId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @PreAuthorize("@securityChecks.canEditReview(#reviewDto.userId, #reviewId)")
    public Response reviewEdition(@PathParam("reviewId") final Long reviewId,
                                  @Valid final NewReviewDto reviewDto) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        if(reviewDto == null) {
            throw new BadRequestException("Must include edit data");
        }

        final Review oldReview = rs.findById(reviewId).get();
        User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        if(!oldReview.getUser().getUserName().equals(user.getUserName())){
            LOGGER.warn("PUT /{}: The editor is not owner of the review", uriInfo.getPath());
            throw new ForbiddenException();
        }

        rs.updateReview(reviewDto.getName(), reviewDto.getDescription(), reviewDto.getRating(), reviewId);
        return Response.noContent().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Review reputation-------------------------------------------------
//    Endpoint para likear una review
    //TODO Revisar este POST, deberia retornar ok o created y la uri o un mensaje
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/{reviewId}/thumbUp")
    @PreAuthorize("@securityChecks.checkUser(#basicRequestDto.userId)")
    public Response reviewThumbUpPost(@Valid BasicRequestDto basicRequestDto,
                                      @PathParam("reviewId") final Long reviewId) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());
        Review review = rs.getReview(reviewId).orElseThrow(ReviewNotFoundException::new);
        User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        rs.thumbUpReview(review,loggedUser);
        LOGGER.info("POST /{}: Thumb up successful", uriInfo.getPath());

        return Response.ok(String.format("The user of id %d, thumbUp the review of id %d", loggedUser.getId(), reviewId)).build();
    }

    @DELETE
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/{reviewId}/thumbUp")
    // TODO: Ver como seria el tema de chequeo de usuarios. Mismo problema que con las watchlist del delete
//    @PreAuthorize("@securityChecks.checkUser(#basicRequestDto.userId)")
    public Response reviewThumbUpDelete(@PathParam("reviewId") final Long reviewId) {
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());
        Review review = rs.getReview(reviewId).orElseThrow(ReviewNotFoundException::new);
        User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        // TODO: Ahora tenemos un metodo que hace todo. Ahora habria que dividirlo en 2 disitntos o dejarlo asi, VER
        rs.thumbUpReview(review,loggedUser);
        LOGGER.info("DELETE /{}: Thumb up successful", uriInfo.getPath());

        return Response.noContent().build();
    }


//    Endpoint para deslikear una review
    //TODO Revisar este POST, deberia retornar ok o created y la uri o un mensaje
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/{reviewId}/thumbDown")
    @PreAuthorize("@securityChecks.checkUser(#basicRequestDto.userId)")
    public Response reviewThumbDownPost(@Valid BasicRequestDto basicRequestDto,
                                        @PathParam("reviewId") final long reviewId) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());
        Review review = rs.getReview(reviewId).orElseThrow(ReviewNotFoundException::new);
        User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        rs.thumbDownReview(review,loggedUser);
        LOGGER.info("POST /{}: Thumb down successful", uriInfo.getPath());

        // TODO: Deberia devolver un 201 CREATED o 200 OK, nose bien
        return Response.ok(String.format("The user of id %d, thumbDown the review of id %d", loggedUser.getId(), reviewId)).build();
    }

    @DELETE
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/{reviewId}/thumbDown")
// TODO: Ver como seria el tema de chequeo de usuarios. Mismo problema que con las watchlist del delete
//    @PreAuthorize("@securityChecks.checkUser(#basicRequestDto.userId)")
    public Response reviewThumbDownDelete(@PathParam("reviewId") final long reviewId) {
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());
        Review review = rs.getReview(reviewId).orElseThrow(ReviewNotFoundException::new);
        User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        // TODO: Ahora tenemos un metodo que hace todo. Ahora habria que dividirlo en 2 disitntos o dejarlo asi, VER
        rs.thumbDownReview(review,loggedUser);
        LOGGER.info("DELETE /{}: Thumb down successful", uriInfo.getPath());

        return Response.noContent().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------

    // * ---------------------------------------------Review reports-------------------------------------------------
    @GET
    @Path("/reports")
    public Response getCommentReport(@QueryParam("page") @DefaultValue("1") Integer page,
                                     @QueryParam(value = "reason") ReportReason reason) {
        PageWrapper<ReviewReport> reviewsReported = rrs.getReportedReviews(reason, page, REPORTS_AMOUNT);
        Collection<ReviewReportDto> reviewsReportedListDto = ReviewReportDto.mapReviewReportToReviewReportDto(uriInfo, reviewsReported.getPageContent());
        LOGGER.info("GET /{}: Reported reviews list success for admin user", uriInfo.getPath());
        Response.ResponseBuilder response = Response.ok(new GenericEntity<Collection<ReviewReportDto>>(reviewsReportedListDto){});
        response.header("Total-Review-Reports",rrs.getReportedReviewsAmount(reason));
        response.header("Total-Comment-Reports",rrs.getReportedCommentsAmount(reason));
        ResponseBuildingUtils.setPaginationLinks(response,reviewsReported , uriInfo);
        return response.build();
    }

    @GET
    @Path("/reports/{reportId}")
    public Response getReviewReport(@PathParam(value = "reportId") final Long reportId) {
        ReviewReport reviewsReported = rrs.getReportedReview(reportId);
        ReviewReportDto reviewReportDto = new ReviewReportDto(uriInfo, reviewsReported);
        LOGGER.info("GET /{}: Return review report {} with success", uriInfo.getPath(), reportId);
        return Response.ok(reviewReportDto).build();

    }

    @POST
    @Path("/{reviewId}/reports")
    @PreAuthorize("@securityChecks.checkUser(#commentReportDto.userId)")
    public Response addReviewReport(@PathParam("reviewId") long reviewId,
                                    @Valid NewReportCommentDto commentReportDto) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());
        if(commentReportDto==null) {
            throw new BadRequestException("Must include report data");
        }
        final Review review = rs.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);

        final Long reportId = rrs.addReport(review, user, commentReportDto.getReportType());
        LOGGER.info("POST /{}: Review {} reported", uriInfo.getPath(), review.getId());
        final URI location = uriInfo.getBaseUriBuilder().path("/reviews").path("/reports").path(String.valueOf(reportId)).build();
        return Response.created(location).build();
    }

    @DELETE
    @Path("/{reviewId}/reports")
    public Response deleteReport(@PathParam("reviewId") long reviewId) {
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());
        rrs.removeReports("review", reviewId);
        LOGGER.info("DELETE /{}: {} on contentId {} report deleted", uriInfo.getPath(), "Review", reviewId);
        return Response.noContent().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------
    
}
