package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Content;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;

import ar.edu.itba.paw.services.EmailService;
import ar.edu.itba.paw.services.ReviewService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.auth.PawUserDetails;
import ar.edu.itba.paw.webapp.exceptions.MethodNotAllowedException;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.EditProfile;
import ar.edu.itba.paw.webapp.form.LoginForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.naming.Context;
import javax.validation.Valid;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class HelloWorldController {

    private final UserService us;
    private final ContentService cs;
    private final ReviewService rs;
    private final EmailService es;
    private final int ELEMS_AMOUNT = 15;

    protected AuthenticationManager authenticationManager;

    @Autowired  //Para indicarle que este es el constructor que quiero que use
    public HelloWorldController(final UserService us, final ContentService cs, ReviewService rs, EmailService es,AuthenticationManager authenticationManager){
        this.us = us;
        this.cs = cs;
        this.rs = rs;
        this.es = es;
        this.authenticationManager=authenticationManager;
    }

    // * ----------------------------------- Movie and Series Info -----------------------------------------------------------------------
    @RequestMapping(value= {"/","page/{pageNum}"})
    public ModelAndView helloWorld(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("pageNum")final Optional<Integer> pageNum,@RequestParam(name = "query", defaultValue = "") final String query) {
        final ModelAndView mav = new ModelAndView("ContentPage");
        mav.addObject("query", query);
        List<Content> contentList = cs.getSearchedContentRandom(query);

        int page= pageNum.orElse(1);
        if(contentList == null) {
            throw new PageNotFoundException();
        } else {
            if(contentList.size() < (page)*ELEMS_AMOUNT){       //Si no llega a completar la pagina entera, que sirva los que pueda
                mav.addObject("allContent", contentList.subList((page-1)*ELEMS_AMOUNT,contentList.size()));
            }else {
                mav.addObject("allContent", contentList.subList((page - 1) * ELEMS_AMOUNT, (page - 1) * ELEMS_AMOUNT + ELEMS_AMOUNT));
            }
            mav.addObject("amountPages",(int)Math.ceil((double) contentList.size()/(double)ELEMS_AMOUNT));
            mav.addObject("pageSelected",page);
            mav.addObject("contentType", "movies");
            mav.addObject("genre","ANY");
            mav.addObject("durationFrom","ANY");
            mav.addObject("durationTo","ANY");
            mav.addObject("contentType", "all");
        }

        try {
            String userEmail = userDetails.getUsername();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());

        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
        }
        return mav;
    }


    @RequestMapping(value= {"/{type:movies|series}","/{type:movies|series}/page/{pageNum}"})
    public ModelAndView contentType(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("type") final String type,@PathVariable("pageNum")final Optional<Integer> pageNum) {
        String auxType = null;
        final ModelAndView mav = new ModelAndView("ContentPage");
        if(Objects.equals(type, "movies")) {
            auxType = "movie";
        } else if(Objects.equals(type, "series")) {
            auxType = "serie";
        }
        int page= pageNum.orElse(1);
        List<Content> contentList = cs.getAllContent(auxType, "ANY");
        if( contentList == null) {
            throw new PageNotFoundException();
        } else {
            if(contentList.size() < (page)*ELEMS_AMOUNT){       //Si no llega a completar la pagina entera, que sirva los que pueda
                mav.addObject("allContent", contentList.subList((page-1)*ELEMS_AMOUNT,contentList.size()));
            }else {
                mav.addObject("allContent", contentList.subList((page - 1) * ELEMS_AMOUNT, (page - 1) * ELEMS_AMOUNT + ELEMS_AMOUNT));
            }
            if(Objects.equals(type, "movies") || Objects.equals(type, "series")){
                mav.addObject("contentType", type);
            }else {
                mav.addObject("contentType", "all");
            }
            mav.addObject("amountPages",(int)Math.ceil((double) contentList.size()/(double)ELEMS_AMOUNT));
            mav.addObject("pageSelected",page);
            mav.addObject("genre","ANY");
            mav.addObject("durationFrom","ANY");
            mav.addObject("durationTo","ANY");
        }

        try {
            String userEmail = userDetails.getUsername();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());

        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
        }
        return mav;
    }

    @RequestMapping(value = {"/search", "/page/{pageNum}"})
    public ModelAndView search(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("pageNum")final Optional<Integer> pageNum, @RequestParam(name = "query", defaultValue = "") final String query) {
        final ModelAndView mav = new ModelAndView("ContentPage");

        mav.addObject("query", query);
        List<Content> contentList = cs.getSearchedContent(query);
        mav.addObject("contentType", "all");

        int page= pageNum.orElse(1);

        if(contentList == null) {
            throw new PageNotFoundException();
        }else {
            if(contentList.size() < (page)*ELEMS_AMOUNT){       //Si no llega a completar la pagina entera, que sirva los que pueda
                mav.addObject("allContent", contentList.subList((page-1)*ELEMS_AMOUNT,contentList.size()));
            }else {
                mav.addObject("allContent", contentList.subList((page - 1) * ELEMS_AMOUNT, (page - 1) * ELEMS_AMOUNT + ELEMS_AMOUNT));
            }
            mav.addObject("amountPages", (int) Math.ceil((double) contentList.size() / (double) ELEMS_AMOUNT));
            mav.addObject("pageSelected", page);
        }

        try {
            String userEmail = userDetails.getUsername();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());

        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
        }
        return mav;
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


        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
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

    // * -------------------------------------------------------------------------------------------------------------------------------------


    // *  ----------------------------------- Movies and Serie Filters -----------------------------------------------------------------------

    @RequestMapping(value = {"/{type:movies|series|all}/filters" , "/{type:movies|series|all}/filters/page/{pageNum}"})
    public ModelAndView moviesWithFilters(
            @AuthenticationPrincipal PawUserDetails userDetails,
            @PathVariable("type") final String type,
            @PathVariable("pageNum")final Optional<Integer> pageNum,
            @RequestParam(name = "durationFrom",defaultValue = "ANY",required = false)final String durationFrom,
            @RequestParam(name = "durationTo",defaultValue = "ANY",required = false)final String durationTo,
            @RequestParam(name = "genre", defaultValue = "ANY",required = false)final String genre,
            @RequestParam(name = "sorting", defaultValue = "ANY", required = false) final String sorting) {

        ModelAndView mav = null;
        int page = pageNum.orElse(1);
        String auxType = null;

        if(Objects.equals(type, "movies")) {
            auxType = "movie";
        } else if(Objects.equals(type, "series")) {
            auxType = "serie";
        } else {
            auxType = "all";
        }

        mav = new ModelAndView("ContentPage");
        mav.addObject("genre",genre);
        mav.addObject("durationFrom",durationFrom);
        mav.addObject("durationTo",durationTo);
        mav.addObject("sorting", sorting);

        List<Content> contentListFilter;

        if(!Objects.equals(genre, "ANY") && Objects.equals(durationFrom, "ANY")) {
            contentListFilter = cs.findByGenre(auxType, genre, sorting) ;
        } else if(Objects.equals(genre, "ANY") && !Objects.equals(durationFrom, "ANY")) {
            contentListFilter = cs.findByDuration(auxType, Integer.parseInt(durationFrom), Integer.parseInt(durationTo), sorting);
        } else if(!Objects.equals(durationFrom, "ANY") && !Objects.equals(genre, "ANY")) {    // Caso de que si los filtros estan vacios
            contentListFilter = cs.findByDurationAndGenre(auxType, genre, Integer.parseInt(durationFrom), Integer.parseInt(durationTo), sorting);
        } else{
            contentListFilter = cs.getAllContent(auxType, sorting);
        }

        if(contentListFilter == null) {
            throw new PageNotFoundException();
        } else {
            if(contentListFilter.size() < (page)*ELEMS_AMOUNT){       //Si no llega a completar la pagina entera, que sirva los que pueda
                mav.addObject("allContent", contentListFilter.subList((page-1)*ELEMS_AMOUNT,contentListFilter.size()));
            }else {
                mav.addObject("allContent", contentListFilter.subList((page-1)*ELEMS_AMOUNT,(page-1)*ELEMS_AMOUNT + ELEMS_AMOUNT));
            }
            mav.addObject("amountPages",Math.ceil((double)contentListFilter.size()/(double)ELEMS_AMOUNT));
            mav.addObject("pageSelected",page);
            mav.addObject("contentType", type);
        }

        try {
            String userEmail = userDetails.getUsername();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());

        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
        }

        return mav;
    }

    // * -----------------------------------------------------------------------------------------------------------------------


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

        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
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

    // * -----------------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- login Page -----------------------------------------------------------------------
    @RequestMapping(value = "/login/{loginStage:sign-in}", method = {RequestMethod.GET})
    public ModelAndView emailForm(@AuthenticationPrincipal PawUserDetails userDetails,HttpServletRequest request, @PathVariable("loginStage") final String loginStage, @RequestParam(value = "error",required = false)Optional<Boolean> error) {
        final ModelAndView mav = new ModelAndView("logInPage");
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("url_prior_login", referrer);
        mav.addObject("loginStage", loginStage);
        try {
            String userEmail = userDetails.getUsername();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());

        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
        }
        if(error.isPresent()){
            mav.addObject("error",true);
        }else{
            mav.addObject("error",false);
        }
        return mav;
    }


    @RequestMapping(value = "/login/{loginStage:sign-up|forgot-password}", method = {RequestMethod.GET})
    public ModelAndView emailForm(@AuthenticationPrincipal PawUserDetails userDetails, @ModelAttribute("loginForm") final LoginForm loginForm,@PathVariable("loginStage") final String loginStage) {
        if(!Objects.equals(loginStage, "sign-up") && !Objects.equals(loginStage, "forgot-password") && !Objects.equals(loginStage, "sign-out")) {
            throw new PageNotFoundException();
        }
        final ModelAndView mav = new ModelAndView("logInPage");
        mav.addObject("loginStage", loginStage);
        try {
            String userEmail = userDetails.getUsername();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());

        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
        }
        return mav;
    }

    @RequestMapping(value = "/login/{loginStage:sign-up|forgot-password|sign-out}", method = {RequestMethod.POST})
    public ModelAndView emailFormVerification(@AuthenticationPrincipal PawUserDetails userDetails, @Valid @ModelAttribute("loginForm") final LoginForm loginForm, final BindingResult errors, RedirectAttributes redirectAttributes, @PathVariable("loginStage") final String loginStage,HttpServletRequest request) {
        if(errors.hasErrors()) {
            return emailForm(userDetails, loginForm, loginStage);
        }
        if(Objects.equals(loginStage, "sign-up")) {
            User newUser = new User(null, loginForm.getEmail(), loginForm.getUsername(), loginForm.getPassword(), 0L, null);
            us.register(newUser);
            es.sendRegistrationEmail(newUser);
            authWithAuthManager(request,loginForm.getEmail(),loginForm.getPassword());
        } else if(Objects.equals(loginStage, "forgot-password")) {
            Optional<User> user = us.findByEmail(loginForm.getEmail());
            if(user.isPresent()) {
//                TODO: Descomentar este y borrar el otro
                es.sendForgottenPasswordEmail(user.get());
            } else {
                return emailForm(userDetails, loginForm, loginStage);
            }
        } else {
            throw new PageNotFoundException();
        }

        return new ModelAndView("redirect:/");
    }

    private void authWithAuthManager(HttpServletRequest request,String email,String password){
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        request.getSession();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password, authorities);
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    // * -----------------------------------------------------------------------------------------------------------------------


    // * ------------------------------------------------Profile--------------------------------------------------------------------
    @RequestMapping("/profile/{userId:[0-9]+}")
    public ModelAndView profile(@AuthenticationPrincipal PawUserDetails userDetails,@PathVariable("userId") final long userId) {
        final ModelAndView mav = new ModelAndView("profilePage");
        String userEmail = userDetails.getUsername();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        mav.addObject("user", user);
        mav.addObject("reviews",rs.getAllUserReviews(user.getUserName()));
        return mav;
    }

    @RequestMapping(path = "/profile/{userId:[0-9]+}/profileImage", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] profileImage(@AuthenticationPrincipal PawUserDetails userDetails,@PathVariable("userId") final long userId) {
//        String userEmail = userDetails.getUsername();
        User user = us.findById(userId).orElseThrow(PageNotFoundException::new);
        return user.getImage();
    }

    @RequestMapping(value = "/profile/{userId:[0-9]+}/edit-profile", method = {RequestMethod.GET})
    public ModelAndView profileEdition(@AuthenticationPrincipal PawUserDetails userDetails, @Valid @ModelAttribute("editProfile") final EditProfile editProfile, @PathVariable("userId") final long userId) {
        String userEmail = userDetails.getUsername();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        final ModelAndView mav = new ModelAndView("profileEditionPage");
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/profile/{userId:[0-9]+}/edit-profile", method = {RequestMethod.POST})
    public ModelAndView profileEditionPost(@AuthenticationPrincipal PawUserDetails userDetails,@Valid @ModelAttribute("editProfile") final EditProfile editProfile, final BindingResult errors, @PathVariable("userId") final long userId) throws IOException {
        if(errors.hasErrors()) {
            return profileEdition(userDetails,editProfile, userId);
        }

        User user = us.findById(userId).orElseThrow(PageNotFoundException::new);
        if(editProfile.getPassword() != null && editProfile.getProfilePicture().getBytes() == null) {
            us.setPassword(editProfile.getPassword(), user);
            es.sendRestorePasswordEmail(user);
        } else if(editProfile.getPassword() == null && editProfile.getProfilePicture().getBytes() != null) {
            us.setProfilePicture(editProfile.getProfilePicture().getBytes(), user);
        } else if(editProfile.getPassword() != null && editProfile.getProfilePicture().getBytes() != null) {
            us.setPassword(editProfile.getPassword(), user);
            us.setProfilePicture(editProfile.getProfilePicture().getBytes(), user);
        }

        return new ModelAndView("redirect:/profile/" + userId);
    }

    @RequestMapping("/profile-info/{userName:[a-zA-Z0-9\\s]+}")
    public ModelAndView profileInfo(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("userName") final String userName) {
        final ModelAndView mav = new ModelAndView("profileInfoPage");
        Optional<User> user = us.findByUserName(userName);
        if(user.isPresent()) {
//            mav.addObject("full-access", "no");
            mav.addObject("user", user);
            mav.addObject("reviews",rs.getAllUserReviews(user.get().getUserName()));
        } else {
            throw new PageNotFoundException();
        }

        try {
            String userEmail = userDetails.getUsername();
            User userLogged = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",userLogged.getUserName());
            mav.addObject("userId",userLogged.getId());

        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
        }
        return mav;
    }

    // * -----------------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Edit and delete reviews--------------------------------------------------------------------------
    @RequestMapping(value="/review/{reviewId:[0-9]+}/delete",method = {RequestMethod.POST})
    public ModelAndView deleteReview(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("reviewId") final long reviewId, HttpServletRequest request){
        Review review=rs.findById(reviewId).orElseThrow(PageNotFoundException::new);
        User user=us.findByEmail(userDetails.getUsername()).get();
        if(review.getUserName().equals(user.getUserName())){
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

        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
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

    // * --------------------------------------------------------------------------------------------------------------------



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

