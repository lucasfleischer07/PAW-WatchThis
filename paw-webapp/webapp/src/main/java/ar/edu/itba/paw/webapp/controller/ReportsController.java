package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.exceptions.BadRequestException;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.ReportCommentForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ReportsController {
    private final ReviewService rs;
    private final UserService us;
    private final PaginationService ps;
    private final ReportService rrs;
    private final CommentService ccs;
    private static final int REPORTS_AMOUNT = 3;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportsController.class);
    @Autowired
    public ReportsController(ReviewService rs, UserService us, PaginationService ps, ReportService rrs, CommentService ccs) {
        this.us = us;
        this.rs = rs;
        this.ps = ps;
        this.rrs = rrs;
        this.ccs = ccs;
    }

    private <T> void paginationSetup(ModelAndView mav, int page, List<T> reportedList){
        if(ps.checkPagination(reportedList.size(),page,REPORTS_AMOUNT)){
            LOGGER.warn("Invalid page Number",new PageNotFoundException());
            throw new PageNotFoundException();
        }
        List<T> reportedListPaginated = ps.pagePagination(reportedList, page, REPORTS_AMOUNT);
        mav.addObject("reportedList", reportedListPaginated);
        mav.addObject("amountPages", ps.amountOfContentPages(reportedList.size(),REPORTS_AMOUNT));
        mav.addObject("pageSelected",page);
    }


    // * ----------------------------------- Report Page ---------------------------------------------------------------
    @RequestMapping(value={"/report/reportedContent/{type:reviews|comments}", "/report/reportedContent/{type:reviews|comments}/page/{pageNum:[0-9]+}", "/report/reportedContent/{type:reviews|comments}/filters", "/report/reportedContent/{type:reviews|comments}/filters/page/{pageNum:[0-9]+"},method = {RequestMethod.GET})
    public ModelAndView reportPage(Principal userDetails,
                                   @PathVariable("pageNum")final Optional<Integer> pageNum,
                                   @PathVariable("type") final String type,
                                   @RequestParam(value = "reason",required = false)Optional<ReportReason> reason){
        ModelAndView mav = new ModelAndView("reportedPage");
        User user = us.findByEmail(userDetails.getName()).get();
        mav.addObject("userName", user.getUserName());
        mav.addObject("userId", user.getId());
        List<CommentReport> commentsReportedList = rrs.getReportedComments(reason.orElse(null));
        List<ReviewReport> reviewsReportedList = rrs.getReportedReviews(reason.orElse(null));
        ReportReason reasonGiven = reason.orElse(null);
        mav.addObject("reason",reasonGiven != null ? reasonGiven.toString() : "ANY");
        mav.addObject("reviewsReportedAmount", reviewsReportedList.size());
        mav.addObject("commentsReportedAmount", commentsReportedList.size());

        if(Objects.equals(type, "reviews")) {
            mav.addObject("type", "reviews");
            paginationSetup(mav,pageNum.orElse(1), reviewsReportedList);
        } else if(Objects.equals(type, "comments")) {
            mav.addObject("type", "comments");
            paginationSetup(mav,pageNum.orElse(1), commentsReportedList);
        } else {
            throw new PageNotFoundException();
        }


        return mav;
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Review Report ------------------------------------------------------------
    @RequestMapping(value="/report/review/{reviewId:[0-9]+}",method = {RequestMethod.POST})
    public ModelAndView reportReview(Principal userDetails,
                                      @PathVariable("reviewId") final long reviewId,
                                      @ModelAttribute("reportReviewForm") final ReportCommentForm reportReviewForm,
                                      @ModelAttribute("reportCommentForm") final ReportCommentForm reportCommentForm,
                                      HttpServletRequest request){
        Review review=rs.findById(reviewId).orElseThrow(PageNotFoundException::new);
        User user=us.findByEmail(userDetails.getName()).get();
        rrs.addReport(review, user, reportReviewForm.getReportType());
        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:"+ referer);
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Comment Report ------------------------------------------------------------
    @RequestMapping(value="/report/comment/{commentId:[0-9]+}",method = {RequestMethod.POST})
    public ModelAndView reportComment(Principal userDetails,
                                      @PathVariable("commentId") final long commentId,
                                      @ModelAttribute("reportReviewForm") final ReportCommentForm reportReviewForm,
                                      @ModelAttribute("reportCommentForm") final ReportCommentForm reportCommentForm,
                                      HttpServletRequest request){
        Comment comment=ccs.getComment(commentId).orElseThrow(PageNotFoundException::new);
        User user=us.findByEmail(userDetails.getName()).get();
        if(comment.getUser().getId()==user.getId())
            throw new BadRequestException();
        rrs.addReport(comment, user, reportCommentForm.getReportType());
        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:"+ referer);
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Delete Report From Reported List-------------------------------------------
    @RequestMapping(value = "/report/reportedContent/{type:review|comment}/{contentId:[0-9]+}/report/delete", method = {RequestMethod.POST})
    public ModelAndView deleteReportFromReportedList(@PathVariable("type")final String type,
                                                     @PathVariable("contentId")final long contentId,
                                                     HttpServletRequest request){
        rrs.removeReports(type, contentId);
        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:"+ referer);
    }
    // * ---------------------------------------------------------------------------------------------------------------


}
