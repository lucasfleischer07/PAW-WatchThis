package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.exceptions.ForbiddenException;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.CommentForm;
import ar.edu.itba.paw.webapp.form.ReportCommentForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ReviewController {
    private final ReviewService rs;
    private final ContentService cs;
    private final UserService us;
    private final PaginationService ps;
    private final CommentService ccs;
    private final ReportService rrs;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

   @Autowired
    public ReviewController(ReviewService rs,ContentService cs,UserService us, CommentService ccs, ReportService rrs,PaginationService ps) {
        this.us = us;
        this.cs = cs;
        this.rs = rs;
        this.ccs = ccs;
        this.rrs = rrs;
        this.ps = ps;
    }

    private void paginationSetup(ModelAndView mav,int page,List<Review> reviewList){
        if(reviewList.size() == 0) {
            mav.addObject("reviews",reviewList);
            mav.addObject("pageSelected",1);
            mav.addObject("amountPages",1);
            return;
        }

        List<Review> reviewListPaginated = ps.reviewPagination(reviewList, page);
        mav.addObject("reviews", reviewListPaginated);

        int amountOfPages = ps.amountOfContentPages(reviewList.size());
        mav.addObject("amountPages", amountOfPages);
        mav.addObject("pageSelected",page);
    }


    // * ----------------------------------- Movies and Series Info page -----------------------------------------------
    @RequestMapping(value={"/{type:movie|serie}/{contentId:[0-9]+}","/{type:movie|serie}/{contentId:[0-9]+}/page/{pageNum:[0-9]+}"})
    public ModelAndView reviews(Principal userDetails,
                                @PathVariable("contentId")final long contentId,
                                @PathVariable("type") final String type,
                                @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
                                @ModelAttribute("reportReviewForm") final ReportCommentForm reportReviewForm,
                                @ModelAttribute("reportCommentForm") final ReportCommentForm reportCommentForm,
                                @PathVariable("pageNum")final Optional<Integer> pageNum,
                                HttpServletRequest request) {
        final ModelAndView mav = new ModelAndView("infoPage");
        Content content=cs.findById(contentId).orElseThrow(PageNotFoundException::new);
        mav.addObject("details", content);
        List<Review> reviewList = rs.getAllReviews(content);
        User user=null;
        if(reviewList == null) {
            LOGGER.warn("Cant find a the content specified",new PageNotFoundException());
            throw new PageNotFoundException();
        }

        String auxType;
        if (Objects.equals(type, "movie")) {
            auxType = "movies";
        } else if (Objects.equals(type, "serie")) {
            auxType = "series";
        } else {
            auxType = "all";
        }

        mav.addObject("contentId",contentId);
        mav.addObject("type",auxType);

        if(userDetails != null) {
            String userEmail = userDetails.getName();
            user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());
            rs.userLikeAndDislikeReviewsId(user.getUserVotes());
            mav.addObject("userLikeReviews", rs.getUserLikeReviews());
            mav.addObject("userDislikeReviews", rs.getUserDislikeReviews());

            Optional<Long> isInWatchList = us.searchContentInWatchList(user, contentId);
            if(isInWatchList.get() != -1) {
                mav.addObject("isInWatchList",isInWatchList);
            } else {
                mav.addObject("isInWatchList","null");
            }

            Optional<Long> isInViewedList = us.searchContentInViewedList(user, contentId);
            if(isInViewedList.get() != -1) {
                mav.addObject("isInViewedList",isInViewedList);
            } else {
                mav.addObject("isInViewedList","null");
            }

            if(user.getRole().equals("admin")) {
                mav.addObject("admin",true);
            } else {
                mav.addObject("admin",false);
            }
        } else {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
            mav.addObject("isInWatchList","null");
            mav.addObject("isInViewedList","null");
            mav.addObject("admin",false);
            mav.addObject("userLikeReviews", rs.getUserLikeReviews());
            mav.addObject("userDislikeReviews", rs.getUserDislikeReviews());
        }

        reviewList = rs.sortReviews(user,reviewList);
        paginationSetup(mav,pageNum.orElse(1),reviewList);
        request.getSession().setAttribute("referer","/"+type+"/"+contentId+(pageNum.isPresent()?"/page/"+pageNum.get():""));
        return mav;
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Movies and Series Review Creation------------------------------------------
    @RequestMapping(value = "/reviewForm/{type:movie|serie}/{id:[0-9]+}/{userId:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView reviewFormCreate(Principal userDetails,
                                         @ModelAttribute("registerForm") final ReviewForm reviewForm,
                                         @PathVariable("id")final long id,
                                         @PathVariable("type")final String type) {
        final ModelAndView mav = new ModelAndView("reviewRegistrationPage");
        mav.addObject("details", cs.findById(id).orElseThrow(PageNotFoundException::new));
        if(Objects.equals(type, "movie")) {
            mav.addObject("type", "movies");
        } else if(Objects.equals(type, "serie")) {
            mav.addObject("type", "series");
        }
        if(userDetails != null) {
            String userEmail = userDetails.getName();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());
            if(user.getRole().equals("admin")) {
                mav.addObject("admin",true);
            } else {
                mav.addObject("admin",false);
            }
        } else {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
            mav.addObject("admin",false);
        }
        return mav;
    }

    @RequestMapping(value = "/reviewForm/{type:movie|serie}/{id:[0-9]+}/{userId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView reviewFormMovie(Principal userDetails,
                                        @Valid @ModelAttribute("registerForm") final ReviewForm form,
                                        final BindingResult errors,
                                        @PathVariable("id")final long id,
                                        @PathVariable("type")final String type,
                                        @PathVariable("userId")final long userId,
                                        HttpServletRequest request) {
        if(errors.hasErrors()) {
            return reviewFormCreate(userDetails,form,id,type);
        }
        if(form.getRating() < 0 || form.getRating() > 5) {
            return reviewFormCreate(userDetails,form,id,type);
        }

        Optional<User> user = us.findByEmail(userDetails.getName());
        try {
            Content content= cs.findById(id).orElseThrow(PageNotFoundException ::new);
            rs.addReview(form.getName(),form.getDescription(), form.getRating(), type,user.get(),content);
        }
        catch(DuplicateKeyException e){
            ModelAndView mav = reviewFormCreate(userDetails,form,id,type);
            mav.addObject("errorMsg","You have already written a review for this " + type + ".");
            return mav;
        }

        ModelMap model =new ModelMap();
        String referer = request.getSession().getAttribute("referer").toString();
        return new ModelAndView("redirect:" + (referer==null?"/":referer),model);

    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Review delete-----------------------------------------------------
    @RequestMapping(value="/review/{reviewId:[0-9]+}/delete",method = {RequestMethod.POST})
    public ModelAndView deleteReview(Principal userDetails,
                                     @PathVariable("reviewId") final long reviewId,
                                     HttpServletRequest request){
        Review review=rs.findById(reviewId).orElseThrow(PageNotFoundException::new);
        User user=us.findByEmail(userDetails.getName()).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(review.getUser().getUserName().equals(user.getUserName())){
            rs.deleteReview(reviewId);
            String referer = request.getHeader("Referer");
            return new ModelAndView("redirect:"+ referer);
        } else if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            rrs.delete(review, null);
            String referer = request.getHeader("Referer");
            return new ModelAndView("redirect:"+ referer);
        } else {
            LOGGER.warn("Not allowed to delete",new ForbiddenException());
            throw new ForbiddenException();
        }
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Review edition----------------------------------------------------
    @RequestMapping(value = "/reviewForm/edit/{type:movie|serie}/{contentId:[0-9]+}/{reviewId:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView reviewFormEdition(Principal userDetails,
                                          @ModelAttribute("registerForm") final ReviewForm reviewForm,
                                          @PathVariable("contentId")final long contentId,
                                          @PathVariable("reviewId")final long reviewId,
                                          @PathVariable("type")final String type,
                                          HttpServletRequest request) {
        final ModelAndView mav = new ModelAndView("reviewEditionPage");
        mav.addObject("details", cs.findById(contentId).orElseThrow(PageNotFoundException::new));
        if(Objects.equals(type, "movie")) {
            mav.addObject("type", "movies");
        } else if(Objects.equals(type, "serie")) {
            mav.addObject("type", "series");
        }
        Optional<Review> oldReview = rs.findById(reviewId);
        if(!oldReview.isPresent()){
            LOGGER.warn("Cant find a the review specified", new PageNotFoundException());
            throw new PageNotFoundException();
        }
        if(userDetails != null) {
            String userEmail = userDetails.getName();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("user",user);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());
            if(user.getRole().equals("admin")){
                mav.addObject("admin",true);
            } else {
                mav.addObject("admin",false);
            }

            if(!oldReview.get().getUser().getUserName().equals(us.findByEmail(userDetails.getName()).get().getUserName())){
                LOGGER.warn("The editor is not owner of the review",new ForbiddenException());
                throw new ForbiddenException();
            }

        } else {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
            mav.addObject("admin",false);
        }

        reviewForm.setDescription(oldReview.get().getDescription());
        reviewForm.setRating(oldReview.get().getRating());
        reviewForm.setName(oldReview.get().getName());
        String referer = request.getHeader("Referer");
        mav.addObject("backLink",referer);
        mav.addObject("reviewInfo", rs.findById(reviewId).orElseThrow(PageNotFoundException::new));
        return mav;
    }

    @RequestMapping(value = "/reviewForm/edit/{type:movie|serie}/{contentId:[0-9]+}/{reviewId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView reviewFormEditionPost(Principal userDetails,
                                              @Valid @ModelAttribute("registerForm") final ReviewForm form,
                                              final BindingResult errors, @PathVariable("type")final String type,
                                              @PathVariable("contentId")final long contentId,
                                              @PathVariable("reviewId")final long reviewId,
                                              HttpServletRequest request) {
        if(errors.hasErrors()) {
            return reviewFormEdition(userDetails,form,contentId,reviewId,type,request);
        }
        if(form.getRating() < 0 || form.getRating() > 5) {
            return reviewFormEdition(userDetails,form,contentId,reviewId,type,request);
        }

        Optional<Review> oldReview = rs.findById(reviewId);
        if(!oldReview.isPresent()){
            LOGGER.warn("Cant find a the review specified",new PageNotFoundException());
            throw new PageNotFoundException();
        }

        Optional<User> user = us.findByEmail(userDetails.getName());
        if(!oldReview.get().getUser().getUserName().equals(user.get().getUserName())){
            LOGGER.warn("The editor is not owner of the review",new PageNotFoundException());
            throw new ForbiddenException();
        }
        rs.updateReview(form.getName(), form.getDescription(), form.getRating(), reviewId);
        ModelMap model =new ModelMap();
        String referer = request.getSession().getAttribute("referer").toString();
        return new ModelAndView("redirect:" + (referer==null?"/":referer),model);

    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Review reputation-------------------------------------------------
    @RequestMapping(value = "/reviewReputation/thumbUp/{reviewId:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView reviewReputationThumbUpPost(Principal userDetails,
                                                    @PathVariable("reviewId")final long reviewId,
                                                    HttpServletRequest request) {
       if(us.findByEmail(userDetails.getName()).isPresent()) {
            Review review = rs.getReview(reviewId).orElseThrow(PageNotFoundException ::new);
            User loggedUser=us.findByEmail(userDetails.getName()).get();
            rs.thumbUpReview(review,loggedUser);
        } else {
            LOGGER.warn("Not allowed to thumb up the review",new ForbiddenException());
            throw new ForbiddenException();
        }

        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:"+ referer);
    }

    @RequestMapping(value = "/reviewReputation/thumbDown/{reviewId:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView reviewReputationThumbDownPost(Principal userDetails,
                                                      @PathVariable("reviewId")final long reviewId,
                                                      HttpServletRequest request) {
        if(us.findByEmail(userDetails.getName()).isPresent()){
            Review review = rs.getReview(reviewId).orElseThrow(PageNotFoundException ::new);
            User loggedUser=us.findByEmail(userDetails.getName()).get();
            rs.thumbDownReview(review,loggedUser);
            String referer = request.getHeader("Referer");
            return new ModelAndView("redirect:"+ referer);
        } else {
            LOGGER.warn("Not allowed to thumb down the review",new ForbiddenException());
            throw new ForbiddenException();
        }
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Comments add -----------------------------------------------------
    @RequestMapping(value = "/review/add/comment/{reviewId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView commentReview(Principal userDetails,
                                        @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
                                        final BindingResult errors,
                                        @PathVariable("reviewId")final long reviewId,
                                        HttpServletRequest request) {

        Optional<User> user = us.findByEmail(userDetails.getName());
        Optional<Review> review = rs.getReview(reviewId);

        if(!review.isPresent()) {
            throw new PageNotFoundException();
        }

        ccs.addComment(review.get(), user.get(), commentForm.getComment());

        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:"+ referer);
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Comments delete---------------------------------------------------
    @RequestMapping(value = "/comment/{commentId:[0-9]+}/delete", method = {RequestMethod.POST})
    public ModelAndView commentReviewDelete(Principal userDetails,
                                      @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
                                      @PathVariable("commentId")final long commentId,
                                      HttpServletRequest request) {

        Optional<User> user = us.findByEmail(userDetails.getName());
        Optional<Comment> deleteComment = ccs.getComment(commentId);
        if(!deleteComment.isPresent()) {
            throw new PageNotFoundException();
        }


        if(user.get().getUserName().equals(deleteComment.get().getUser().getUserName())) {
            ccs.deleteComment(deleteComment.get());
        } else {
            rrs.delete(deleteComment, deleteComment.get().getReports());
        }

        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:"+ referer);

    }

    // * ---------------------------------------------------------------------------------------------------------------


}
