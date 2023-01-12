package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.dto.response.CommentReportDto;
import ar.edu.itba.paw.webapp.exceptions.BadRequestException;
import ar.edu.itba.paw.webapp.exceptions.ForbiddenException;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.ReportCommentForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    @Path("/reportedContent/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserWatchList(@PathParam("type") final String type,
                                     @QueryParam("page")@DefaultValue("1")final int page,
                                     @RequestParam(value = "reason",required = false)Optional<ReportReason> reason) {
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);
        if(!Objects.equals(user.getRole(), "ROLE_ADMIN")) {
            LOGGER.warn("GET /{}: Login user {} not an admin", uriInfo.getPath(), user.getId());
            throw new ForbiddenException();
        }

        if(Objects.equals(type, "reviews")) {
            List<ReviewReport> reviewsReportedList = rrs.getReportedReviews(reason.orElse(null));
            LOGGER.info("GET /{}: Reported reviews list success for admin user {}", uriInfo.getPath(), user.getId());
//            TODO: meter el return aca
            return null;
        } else if(Objects.equals(type, "comments")) {
            List<CommentReport> commentsReportedList = rrs.getReportedComments(reason.orElse(null));
            LOGGER.info("GET /{}: Reported comments list success for admin user {}", uriInfo.getPath(), user.getId());
//            TODO: meter el return aca
            return null;
        } else {
            throw new PageNotFoundException();
        }
    }

//    @RequestMapping(value={"/report/reportedContent/{type:reviews|comments}", "/report/reportedContent/{type:reviews|comments}/page/{pageNum:[0-9]+}", "/report/reportedContent/{type:reviews|comments}/filters", "/report/reportedContent/{type:reviews|comments}/filters/page/{pageNum:[0-9]+}"},method = {RequestMethod.GET})
//    public ModelAndView reportPage(Principal userDetails,
//                                   @PathVariable("pageNum")final Optional<Integer> pageNum,
//                                   @PathVariable("type") final String type,
//                                   @RequestParam(value = "reason",required = false)Optional<ReportReason> reason){
//        ModelAndView mav = new ModelAndView("reportedPage");
//        User user = us.findByEmail(userDetails.getName()).get();
//        mav.addObject("userName", user.getUserName());
//        mav.addObject("userId", user.getId());
//        List<CommentReport> commentsReportedList = rrs.getReportedComments(reason.orElse(null));
//        List<ReviewReport> reviewsReportedList = rrs.getReportedReviews(reason.orElse(null));
//        ReportReason reasonGiven = reason.orElse(null);
//        mav.addObject("reason",reasonGiven != null ? reasonGiven.toString() : "ANY");
//        mav.addObject("reviewsReportedAmount", reviewsReportedList.size());
//        mav.addObject("commentsReportedAmount", commentsReportedList.size());
//
//        if(pageNum.isPresent()) {
//            if (Objects.equals(type, "reviews") && (reviewsReportedList.size() <= REPORTS_AMOUNT * (pageNum.get() - 1)) || pageNum.get()<0) {
//                return new ModelAndView("redirect:/report/reportedContent/" + type +"/" + (reason.isPresent() ? "filters"+"/page/" + (((reviewsReportedList.size()-1) / 5)+1) +"?reason=" + reason.get() : "page/" + (((reviewsReportedList.size()-1) / 5)+1)));
//            }
//            else if (Objects.equals(type, "comments") && (commentsReportedList.size() <= REPORTS_AMOUNT * (pageNum.get() - 1))||pageNum.get()<0) {
//                return new ModelAndView("redirect:/report/reportedContent/" + type +"/" + (reason.isPresent() ? "filters"+"/page/" + (((commentsReportedList.size()-1) / 5)+1) +"?reason=" + reason.get() : "page/" + (((commentsReportedList.size()-1) / 5)+1)));
//
//            }
//        }
//
//        if(Objects.equals(type, "reviews")) {
//            mav.addObject("type", "reviews");
//            paginationSetup(mav,pageNum.orElse(1), reviewsReportedList);
//        } else if(Objects.equals(type, "comments")) {
//            mav.addObject("type", "comments");
//            paginationSetup(mav,pageNum.orElse(1), commentsReportedList);
//        } else {
//            throw new PageNotFoundException();
//        }
//
//
//        return mav;
//    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Review Report ------------------------------------------------------------
    @PUT
    @Path("/report/review/{reviewId}")
    public Response addReviewReport(@PathParam("reviewId") long reviewId,
                                  @Valid CommentReportDto commentReportDto) {
        final Review review = rs.findById(reviewId).orElseThrow(PageNotFoundException::new);
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
    @PUT
    @Path("/report/comment/{commentId}")
    public Response addCommentReport(@PathParam("commentId") long commentId,
                                     @Valid CommentReportDto commentReportDto) {
        final Comment comment = ccs.getComment(commentId).orElseThrow(PageNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);
        rrs.addReport(comment, user, commentReportDto.getReportReason().toString());

        LOGGER.info("PUT /{}: Comment {} reported", uriInfo.getPath(), comment.getCommentId());
        return Response.noContent().build();
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
    @Path("/report/deleteReport/{type}/{reportId}")
    public Response deleteReport(@PathParam("reportId") long reportId,
                                 @PathParam("type") String type) {
        rrs.removeReports(type, reportId);
        LOGGER.info("DELETE /{}: {} {} report deleted", uriInfo.getPath(), type, reportId);
        return Response.noContent().build();
    }


//    @RequestMapping(value = "/report/reportedContent/{type:review|comment}/{contentId:[0-9]+}/report/delete", method = {RequestMethod.POST})
//    public ModelAndView deleteReportFromReportedList(@PathVariable("type")final String type,
//                                                     @PathVariable("contentId")final long contentId,
//                                                     HttpServletRequest request){
//        rrs.removeReports(type, contentId);
//        String referer = request.getHeader("Referer");
//        return new ModelAndView("redirect:"+ referer);
//    }
    // * ---------------------------------------------------------------------------------------------------------------
}