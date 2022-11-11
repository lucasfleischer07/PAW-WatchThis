package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.ReportCommentForm;
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


    @Autowired
    public ReportsController(ReviewService rs, UserService us, PaginationService ps, ReportService rrs, CommentService ccs) {
        this.us = us;
        this.rs = rs;
        this.ps = ps;
        this.rrs = rrs;
        this.ccs = ccs;
    }

    private <T> void paginationSetup(ModelAndView mav, int page, List<T> reportedList){
        if(reportedList.size() == 0) {
            mav.addObject("reportedList", reportedList);
            mav.addObject("reportedListAmount", reportedList.size());
            mav.addObject("pageSelected",1);
            mav.addObject("amountPages",1);
            return;
        }

        List<T> reportedListPaginated = ps.reportPagination(reportedList, page);
        mav.addObject("reportedList", reportedListPaginated);

        int amountOfPages = ps.amountOfContentPages(reportedList.size());
        mav.addObject("amountPages", amountOfPages);
        mav.addObject("pageSelected",page);
    }


    // * ----------------------------------- Report Page ---------------------------------------------------------------
    @RequestMapping(value={"/report/reportedContent/{type:reviews|comments}", "/report/reportedContent/{type:reviews|comments}/page/{pageNum:[0-9]+}"},method = {RequestMethod.GET})
    public ModelAndView reportPage(Principal userDetails,
                                   @PathVariable("pageNum")final Optional<Integer> pageNum,
                                   @RequestParam(value = "reason",required = false)Optional<ReportReason> reason){
                                   @PathVariable("type") final String type,
        ModelAndView mav = new ModelAndView("reportedPage");
        User user = us.findByEmail(userDetails.getName()).get();
        mav.addObject("userName", user.getUserName());
        mav.addObject("userId", user.getId());
        if(user.getRole().equals("admin")){
            mav.addObject("admin",true);
        } else {
            mav.addObject("admin",false);
        }

        List<CommentReport> commentReportedList = rrs.getReportedComments(reason.orElse(null));
        List<ReviewReport> reviewReportedList = rrs.getReportedReviews(reason.orElse(null));
        if(Objects.equals(type, "reviews")) {
            mav.addObject("type", "reviews");
            paginationSetup(mav,pageNum.orElse(1), reviewsReportedList);
        } else if(Objects.equals(type, "comments")) {
            mav.addObject("type", "comments");
            paginationSetup(mav,pageNum.orElse(1), commentsReportedList);
        } else {
            throw new PageNotFoundException();
        }

        mav.addObject("reviewsReportedAmount", reviewsReportedList.size());
        mav.addObject("commentsReportedAmount", commentsReportedList.size());
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
        rrs.addReport(comment, user, reportCommentForm.getReportType());
        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:"+ referer);
    }
    // * ---------------------------------------------------------------------------------------------------------------



}
