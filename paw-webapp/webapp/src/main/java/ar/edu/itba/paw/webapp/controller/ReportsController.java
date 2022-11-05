package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.PaginationService;
import ar.edu.itba.paw.services.ReviewService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.ReportCommentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ReportsController {
    private final ReviewService rs;
    private final UserService us;
    private final PaginationService ps;

    @Autowired
    public ReportsController(ReviewService rs, UserService us, PaginationService ps) {
        this.us = us;
        this.rs = rs;
        this.ps = ps;
    }

    private void paginationSetup(ModelAndView mav, int page, List<Review> reportedList){
        if(reportedList.size() == 0) {
            mav.addObject("reportedListContent",reportedList);
            mav.addObject("pageSelected",1);
            mav.addObject("amountPages",1);
            return;
        }

        List<Review> reviewListPaginated = ps.reviewPagination(reportedList, page);
        mav.addObject("reportedListContent", reviewListPaginated);

        int amountOfPages = ps.amountOfContentPages(reportedList.size());
        mav.addObject("amountPages", amountOfPages);
        mav.addObject("pageSelected",page);
    }


    // * ----------------------------------- Report Page ---------------------------------------------------------------
    @RequestMapping(value={"/report/reportedContent", "/report/reportedContent/page/{pageNum:[0-9]+}"},method = {RequestMethod.GET})
    public ModelAndView reportPage(Principal userDetails,
                                   @PathVariable("pageNum")final Optional<Integer> pageNum){
        ModelAndView mav = new ModelAndView("reportedPage");
        User user=us.findByEmail(userDetails.getName()).get();
        mav.addObject("userName", user.getUserName());
        mav.addObject("userId", user.getId());
        if(user.getRole().equals("admin")){
            mav.addObject("admin",true);
        } else {
            mav.addObject("admin",false);
        }

//        TODO: Necesito lo siguiente:
//         - Lista de cosas reportadas
//         - Dentro de la lista, poder distinguir entre comments y reviews
//         - Cantidad de reviews reportadas
//         - Cantidad de comments reportadas
//         - Cantidad de reports que tiene esa review/comment (si puede ser con la primer letra en minuscula, mejor)
//         - Motivos del report, onda la/las categoria/s (si es en froma de un string ya listo, mejor)
//         - Nombre y id del usuario que hizo el comment/review reportado
//         - Nombre y id del contenido donde se hizo el reportado
//         - El texto del comment/review que se reporto, onda, lo que decia esa comment/review

        mav.addObject("commentsReportedList", user.getId());
//        mav.addObject("reviewReportedAmount", user.getId());
//        mav.addObject("commentsReportedAmount", user.getId());


//        paginationSetup(mav,pageNum.orElse(1),reviewList);
        return mav;
    }
    // * ---------------------------------------------------------------------------------------------------------------


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
