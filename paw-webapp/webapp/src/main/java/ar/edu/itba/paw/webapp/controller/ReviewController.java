package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.dto.response.LongDto;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.List;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);
    private static final int REVIEW_AMOUNT = 3;


    // * ----------------------------------- Movies and Series Review Gets ---------------------------------------------
    /*
    TODO: HAY QUE VER COMO MANDAMOS LA CANTIDAD DE PAGINAS Y ESO
     */

    @GET
    @Path("/{contentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reviews(@PathParam("contentId") final long contentId,
                            @QueryParam("pageNumber") @DefaultValue("1") int pageNumber) {
        LOGGER.info("GET /{}: Called",uriInfo.getPath());
        Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        PageWrapper<Review> reviewList = rs.getAllReviews(content,pageNumber,REVIEW_AMOUNT);
        if(reviewList == null) {
            LOGGER.warn("GET /{}: Cant find a the content specifispecified",uriInfo.getPath());
            throw new ContentNotFoundException();
        }
        Collection<ReviewDto> reviewDtoList = ReviewDto.mapReviewToReviewDto(uriInfo, reviewList.getPageContent());

        LOGGER.info("GET /{}: Review list for content {}",uriInfo.getPath(), contentId);

        final Response.ResponseBuilder response = Response.ok(new GenericEntity<Collection<ReviewDto>>(reviewDtoList){});
        ResponseBuildingUtils.setPaginationLinks(response,reviewList , uriInfo);
        return response.build();
    }

    @GET
    @Path("/specificReview/{reviewId}")
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
    @Path("/create/{type}/{contentId}")
    public Response reviews(@PathParam("type") final String type,
                            @PathParam("contentId") final long contentId,
                            @Valid NewReviewDto reviewDto) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());
        if(reviewDto==null)
            throw new BadRequestException("Must include review data");
        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);
        final List<User> userList = cs.getContentReviewers(contentId);
        for (User user2 : userList) {
            if (user2.getId() == user.getId()) {
                return Response.noContent().build();
            }
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
    @Path("/delete/{reviewId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
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
    @Path("/editReview/{reviewId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response reviewEdition(@PathParam("reviewId") final Long reviewId,
                                  @Valid final NewReviewDto reviewDto) {

        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        if(reviewDto==null)
            throw new BadRequestException("Must include edit data");
        final Review oldReview = rs.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new ForbiddenException("User not found"));
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
    @PUT
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/reviewReputation/thumbUp/{reviewId}")
    public Response reviewThumbUp(@PathParam("reviewId") final long reviewId) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        if(us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).isPresent()) {
            Review review = rs.getReview(reviewId).orElseThrow(ReviewNotFoundException::new);
            User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
            rs.thumbUpReview(review,loggedUser);
        } else {
            LOGGER.warn("PUT /{}: Not allowed to thumb up the review", uriInfo.getPath());
            throw new ForbiddenException();
        }
        LOGGER.info("PUT /{}: Thumb up successful", uriInfo.getPath());

        return Response.noContent().build();
    }


//    Endpoint para likear una review
    @PUT
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/reviewReputation/thumbDown/{reviewId}")
    public Response reviewThumbDown(@PathParam("reviewId") final long reviewId) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        if(us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).isPresent()) {
            Review review = rs.getReview(reviewId).orElseThrow(ReviewNotFoundException::new);
            User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
            rs.thumbDownReview(review,loggedUser);
        } else {
            LOGGER.warn("PUT /{}: Not allowed to thumb down the review", uriInfo.getPath());
            throw new ForbiddenException();
        }
        LOGGER.info("PUT /{}: Thumb down successful", uriInfo.getPath());

        return Response.noContent().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


}
