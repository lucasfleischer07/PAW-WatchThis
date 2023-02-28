package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.CommentNotFoundException;
import ar.edu.itba.paw.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.dto.response.CommentReportDto;
import ar.edu.itba.paw.webapp.dto.response.ReviewReportDto;
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
    private PaginationService ps;
    @Autowired
    private ReportService rrs;
    @Autowired
    private CommentService ccs;
    @Context
    private UriInfo uriInfo;
    private static final int REPORTS_AMOUNT = 5;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportsController.class);

//    TODO: VER PAGINACION
//    private <T> void paginationSetup(ModelAndView mav, int page, List<T> reportedList){
//        if(ps.checkPagination(reportedList.size(),page,REPORTS_AMOUNT)){
//            LOGGER.warn("Invalid page Number",new PageNotFoundException());
//            throw new PageNotFoundException();
//        }
//        List<T> reportedListPaginated = ps.pagePagination(reportedList, page, REPORTS_AMOUNT);
//        mav.addObject("reportedList", reportedListPaginated);
//        mav.addObject("amountPages", ps.amountOfContentPages(reportedList.size(),REPORTS_AMOUNT));
//        mav.addObject("pageSelected",page);
//    }


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
            List<ReviewReport> reviewsReportedList = rrs.getReportedReviews(reason);
            Collection<ReviewReportDto> reviewsReportedListDto = ReviewReportDto.mapReviewReportToReviewReportDto(uriInfo, reviewsReportedList);
            LOGGER.info("GET /{}: Reported reviews list success for admin user {}", uriInfo.getPath(), user.getId());
//            TODO: VER EL TEMA DE LA PAGINACION
            return Response.ok(new GenericEntity<Collection<ReviewReportDto>>(reviewsReportedListDto){}).build();
        } else if(Objects.equals(type, "comments")) {
            List<CommentReport> commentsReportedList = rrs.getReportedComments(reason);
            Collection<CommentReportDto> commentReportedListDto = CommentReportDto.mapCommentReportToCommentReportDto(uriInfo, commentsReportedList);
            LOGGER.info("GET /{}: Reported comments list success for admin user {}", uriInfo.getPath(), user.getId());
//            TODO: VER EL TEMA DE LA PAGINACION
            return Response.ok(new GenericEntity<Collection<CommentReportDto>>(commentReportedListDto){}).build();

        } else {
            throw new ContentNotFoundException();
        }
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Review Report ------------------------------------------------------------
    @POST
    @Path("/review/{reviewId}")
    public Response addReviewReport(@PathParam("reviewId") long reviewId,
                                    @Valid CommentReportDto commentReportDto) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        final Review review = rs.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);
        rrs.addReport(review, user, commentReportDto.getReportReason().toString());

        LOGGER.info("PUT /{}: Review {} reported", uriInfo.getPath(), review.getId());
        return Response.noContent().build();
    }

//    @RequestMapping(value="/report/review/{reviewId:[0-9]+}",method = {RequestMethod.POST})
//    public ModelAndView reportReview(Principal userDetails,
//                                      @PathVariable("reviewId") final long reviewId,
//                                      @ModelAttribute("reportReviewForm") final ReportCommentForm reportReviewForm,
//                                      @ModelAttribute("reportCommentForm") final ReportCommentForm reportCommentForm,
//                                      final BindingResult errors,
//                                      HttpServletRequest request){
//
//        if(errors.hasErrors() || reportReviewForm.getReportType() == null) {
//            String referer = request.getHeader("Referer");
//            return new ModelAndView("redirect:"+ referer);
//        }
//
//        Review review=rs.findById(reviewId).orElseThrow(PageNotFoundException::new);
//        User user=us.findByEmail(userDetails.getName()).get();
//        rrs.addReport(review, user, reportReviewForm.getReportType());
//        String referer = request.getHeader("Referer");
//        return new ModelAndView("redirect:"+ referer);
//    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Comment Report ------------------------------------------------------------
    @POST
    @Path("/comment/{commentId}")
    public Response addCommentReport(@PathParam("commentId") long commentId,
                                     @Valid CommentReportDto commentReportDto) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());

        final Comment comment = ccs.getComment(commentId).orElseThrow(CommentNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);
        rrs.addReport(comment, user, commentReportDto.getReportReason().toString());

        LOGGER.info("PUT /{}: Comment {} reported", uriInfo.getPath(), comment.getCommentId());
        return Response.ok().build();
    }

//    @RequestMapping(value="/report/comment/{commentId:[0-9]+}",method = {RequestMethod.POST})
//    public ModelAndView reportComment(Principal userDetails,
//                                      @PathVariable("commentId") final long commentId,
//                                      @ModelAttribute("reportReviewForm") final ReportCommentForm reportReviewForm,
//                                      @ModelAttribute("reportCommentForm") final ReportCommentForm reportCommentForm,
//                                      final BindingResult errors,
//                                      HttpServletRequest request){
//
//        if(errors.hasErrors() || reportCommentForm.getReportType() == null) {
//            String referer = request.getHeader("Referer");
//            return new ModelAndView("redirect:"+ referer);
//        }
//
//        Comment comment=ccs.getComment(commentId).orElseThrow(PageNotFoundException::new);
//        User user=us.findByEmail(userDetails.getName()).get();
//        if(comment.getUser().getId()==user.getId())
//            throw new BadRequestException();
//        rrs.addReport(comment, user, reportCommentForm.getReportType());
//        String referer = request.getHeader("Referer");
//        return new ModelAndView("redirect:"+ referer);
//    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Delete Report From Reported List-------------------------------------------
    @DELETE
    @Path("/deleteReport/{type}/{contentId}")
    public Response deleteReport(@PathParam("contentId") long contentId,
                                 @PathParam("type") String type) {
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());

        rrs.removeReports(type, contentId);
        LOGGER.info("DELETE /{}: {} on contentId {} report deleted", uriInfo.getPath(), type, contentId);
        return Response.noContent().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------
}