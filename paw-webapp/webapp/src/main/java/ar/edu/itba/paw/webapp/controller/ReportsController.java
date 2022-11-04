package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ReviewService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.ReportCommentForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class ReportsController {
    private final ReviewService rs;
    private final UserService us;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    public ReportsController(ReviewService rs, UserService us) {
        this.us = us;
        this.rs = rs;
    }


    // * ----------------------------------- Review Report ------------------------------------------------------------
    @RequestMapping(value="/report/review/{reviewId:[0-9]+}",method = {RequestMethod.POST})
    public ModelAndView reviewComment(Principal userDetails,
                                      @PathVariable("reviewId") final long reviewId,
                                      @ModelAttribute("reportReviewForm") final ReportCommentForm reportReviewForm,
                                      @ModelAttribute("reportCommentForm") final ReportCommentForm reportCommentForm,
                                      HttpServletRequest request){
        Review review=rs.findById(reviewId).orElseThrow(PageNotFoundException::new);
        User user=us.findByEmail(userDetails.getName()).get();
//        rs.addReport(user, review, reportReviewForm.getReportType(), reportReviewForm.getDescriptionReport());
        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:"+ referer);
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Comment Report ------------------------------------------------------------
    @RequestMapping(value="/report/comment/{reviewId:[0-9]+}",method = {RequestMethod.POST})
    public ModelAndView reportComment(Principal userDetails,
                                     @PathVariable("reviewId") final long reviewId,
                                      @ModelAttribute("reportReviewForm") final ReportCommentForm reportReviewForm,
                                      @ModelAttribute("reportCommentForm") final ReportCommentForm reportCommentForm,
                                     HttpServletRequest request){
        Review review=rs.findById(reviewId).orElseThrow(PageNotFoundException::new);
        User user=us.findByEmail(userDetails.getName()).get();
//        rs.addReport(user, review, reportCommentForm.getReportType(), reportCommentForm.getDescriptionReport());
        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:"+ referer);
    }
    // * ---------------------------------------------------------------------------------------------------------------



}
