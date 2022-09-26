package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.ReviewService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.auth.PawUserDetails;
import ar.edu.itba.paw.webapp.exceptions.MethodNotAllowedException;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ReviewController {

    private ReviewService rs;
    private ContentService cs;
    private UserService us;

    @Autowired
    public ReviewController(ReviewService rs,ContentService cs,UserService us){
        this.us = us;
        this.cs = cs;
        this.rs = rs;
    }

    private void HeaderSetUp(ModelAndView mav,PawUserDetails userDetails){
        try {
            String userEmail = userDetails.getUsername();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());
            if(user.getRole().equals("admin")){
                mav.addObject("admin",true);
            }else{
                mav.addObject("admin",false);
            }

        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
            mav.addObject("admin",false);
        }
    }

    @RequestMapping("/{type:movie|serie}/{contentId:[0-9]+}")
    public ModelAndView reviews(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("contentId")final long contentId, @PathVariable("type") final String type) {
        final ModelAndView mav = new ModelAndView("infoPage");
        mav.addObject("details", cs.findById(contentId).orElseThrow(PageNotFoundException::new));
        List<Review> reviewList = rs.getAllReviews(contentId);
        User user=null;
        if(reviewList == null) {
            throw new PageNotFoundException();
        }
        try {
            String userEmail = userDetails.getUsername();
            user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());
            Optional<Long> isInWatchList = us.searchContentInWatchList(user, contentId);
            if(isInWatchList.get() != -1) {
                mav.addObject("isInWatchList",isInWatchList);
            } else {
                mav.addObject("isInWatchList","null");

            }
            if(user.getRole().equals("admin")){
                mav.addObject("admin",true);
            }else{
                mav.addObject("admin",false);
            }
        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
            mav.addObject("isInWatchList","null");
            mav.addObject("admin",false);
        }
        if(user!=null){
            for (Review review:reviewList
            ) {
                if(review.getUserName().equals(user.getUserName())){
                    reviewList.remove(review);
                    reviewList.add(0,review);
                    break;
                }
            }
        }
        mav.addObject("reviews", reviewList);
        return mav;
    }


    // * ----------------------------------- Movies and Series Review -----------------------------------------------------------------------
    @RequestMapping(value = "/reviewForm/{type:movie|serie}/{id:[0-9]+}/{userId:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView reviewFormCreate(@AuthenticationPrincipal PawUserDetails userDetails, @ModelAttribute("registerForm") final ReviewForm reviewForm, @PathVariable("id")final long id, @PathVariable("type")final String type) {
        final ModelAndView mav = new ModelAndView("reviewRegistration");
        mav.addObject("details", cs.findById(id).orElseThrow(PageNotFoundException::new));
        try {
            String userEmail = userDetails.getUsername();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());
            if(user.getRole().equals("admin")){
                mav.addObject("admin",true);
            }else{
                mav.addObject("admin",false);
            }
        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
            mav.addObject("admin",false);
        }
        return mav;
    }

    @RequestMapping(value = "/reviewForm/{type:movie|serie}/{id:[0-9]+}/{userId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView reviewFormMovie(@AuthenticationPrincipal PawUserDetails userDetails, @Valid @ModelAttribute("registerForm") final ReviewForm form, final BindingResult errors, @PathVariable("id")final long id, @PathVariable("type")final String type, @PathVariable("userId")final long userId) {
        if(errors.hasErrors()) {
            return reviewFormCreate(userDetails,form,id,type);
        }
        if(form.getRating() < 0 || form.getRating() > 5) {
            return reviewFormCreate(userDetails,form,id,type);
        }

        Optional<User> user = us.findByEmail(userDetails.getUsername());
        try {
            Review newReview = new Review(null, type, id, form.getName(), form.getDescription(), form.getRating(), user.get().getUserName(),null);
            newReview.setId(id);
            rs.addReview(newReview);
            cs.addContentPoints(id,(int)form.getRating());
        }
        catch(DuplicateKeyException e){
            ModelAndView mav = reviewFormCreate(userDetails,form,id,type);
            mav.addObject("errorMsg","You have already written a review for this " + type + ".");
            return mav;
        }

        ModelMap model =new ModelMap();
        return new ModelAndView("redirect:/" + type + "/" + id, model);
    }


    // * ---------------------------------------------Edit and delete reviews--------------------------------------------------------------------------
    @RequestMapping(value="/review/{reviewId:[0-9]+}/delete",method = {RequestMethod.POST})
    public ModelAndView deleteReview(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("reviewId") final long reviewId, HttpServletRequest request){
        Review review=rs.findById(reviewId).orElseThrow(PageNotFoundException::new);
        User user=us.findByEmail(userDetails.getUsername()).get();
        if(review.getUserName().equals(user.getUserName()) || userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            rs.deleteReview(reviewId);
            cs.decreaseContentPoints(review.getContentId(),review.getRating());
            String referer = request.getHeader("Referer");
            return new ModelAndView("redirect:"+ referer);
        } else throw new MethodNotAllowedException();
    }


    @RequestMapping(value = "/reviewForm/edit/{type:movie|serie}/{contentId:[0-9]+}/{reviewId:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView reviewFormEdition(@AuthenticationPrincipal PawUserDetails userDetails, @ModelAttribute("registerForm") final ReviewForm reviewForm, @PathVariable("contentId")final long contentId, @PathVariable("reviewId")final long reviewId, @PathVariable("type")final String type, HttpServletRequest request) {
        final ModelAndView mav = new ModelAndView("reviewEdition");
        mav.addObject("details", cs.findById(contentId).orElseThrow(PageNotFoundException::new));
        Optional<Review> oldReview = rs.findById(reviewId);
        if(!oldReview.isPresent()){
            throw new PageNotFoundException();
        }
        try {
            String userEmail = userDetails.getUsername();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("user",user);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());
            if(user.getRole().equals("admin")){
                mav.addObject("admin",true);
            }else{
                mav.addObject("admin",false);
            }

        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
            mav.addObject("admin",false);
        }
        if(!oldReview.get().getUserName().equals(us.findByEmail(userDetails.getUsername()).get().getUserName())){
            throw new MethodNotAllowedException();
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
    public ModelAndView reviewFormEditionPost(@AuthenticationPrincipal PawUserDetails userDetails, @Valid @ModelAttribute("registerForm") final ReviewForm form, final BindingResult errors, @PathVariable("type")final String type, @PathVariable("contentId")final long contentId, @PathVariable("reviewId")final long reviewId, HttpServletRequest request) {
        if(errors.hasErrors()) {
            return reviewFormEdition(userDetails,form,contentId,reviewId,type,request);
        }
        if(form.getRating() < 0 || form.getRating() > 5) {
            return reviewFormEdition(userDetails,form,contentId,reviewId,type,request);
        }

        Optional<Review> oldReview = rs.findById(reviewId);
        if(!oldReview.isPresent()){
            throw new PageNotFoundException();
        }

        Optional<User> user = us.findByEmail(userDetails.getUsername());
        if(!oldReview.get().getUserName().equals(user.get().getUserName())){
            throw new MethodNotAllowedException();
        }
        rs.updateReview(form.getName(), form.getDescription(), form.getRating(), reviewId);
        cs.decreaseContentPoints(contentId,oldReview.get().getRating());
        cs.addContentPoints(contentId,form.getRating());
        ModelMap model =new ModelMap();
        return new ModelAndView("redirect:/" + type + "/" + contentId, model);
    }

    // * ----------------------------------- Errors Page -----------------------------------------------------------------------
    @ExceptionHandler(PageNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView PageNotFound(){
        return new ModelAndView("errorPage");
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ModelAndView MethodNotAllowed(){
        return new ModelAndView("errorPage");
    }
    // * -----------------------------------------------------------------------------------------------------------------------


}
