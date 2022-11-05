package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.EditProfile;
import ar.edu.itba.paw.webapp.form.ReportCommentForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
public class UserController {
    private final UserService us;
    private final ContentService cs;
    private final ReviewService rs;
    private final PaginationService ps;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    protected AuthenticationManager authenticationManager;
    @Autowired
    public UserController(final UserService us, final ContentService cs, ReviewService rs, PaginationService ps,AuthenticationManager authenticationManager,PasswordEncoder passwordEncoder){
        this.us = us;
        this.cs = cs;
        this.rs = rs;
        this.ps = ps;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    private void paginationSetup(ModelAndView mav,int page,List<Review> reviewList){
        if(reviewList.size()==0){
            mav.addObject("reviews",reviewList);
            mav.addObject("reviewsAmount",reviewList.size());
            mav.addObject("pageSelected",1);
            mav.addObject("amountPages",1);
            return;
        }

        List<Review> reviewListPaginated = ps.reviewPagination(reviewList, page);
        mav.addObject("reviews", reviewListPaginated);

        int amountOfPages = ps.amountOfReviewPages(reviewList.size());
        mav.addObject("amountPages", amountOfPages);
        mav.addObject("pageSelected",page);
        mav.addObject("reviewsAmount",reviewList.size());

    }


    // * ------------------------------------------------Profile View---------------------------------------------------
    @RequestMapping(value={"/profile","/profile/page/{pageNum:[0-9]+}"})
    public ModelAndView profile(Principal userDetails,
                                @PathVariable("pageNum")final Optional<Integer> pageNum,
                                @ModelAttribute("reportReviewForm") final ReportCommentForm reportReviewForm,
                                @ModelAttribute("reportCommentForm") final ReportCommentForm reportCommentForm,
                                HttpServletRequest request) {
        final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
        final ModelAndView mav = new ModelAndView("profileInfoPage");
        String userEmail = userDetails.getName();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        Optional<String> quote = cs.getContentQuote(locale);
        mav.addObject("quote", quote.get());
        mav.addObject("user", user);
        mav.addObject("userName",user.getUserName());
        mav.addObject("reputationAmount",user.getReputation());
        mav.addObject("userId",user.getId());
        mav.addObject("type","all");

        rs.userLikeAndDislikeReviewsId(user.getUserVotes());
        mav.addObject("userLikeReviews", rs.getUserLikeReviews());
        mav.addObject("userDislikeReviews", rs.getUserDislikeReviews());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            mav.addObject("admin", true);
        } else {
            mav.addObject("admin", false);
        }
        mav.addObject("canPromote",false);

        paginationSetup(mav,pageNum.orElse(1),rs.getAllUserReviews(user));

        request.getSession().setAttribute("referer","/profile");
        return mav;
    }

    @RequestMapping(path = "/profile/{userName:[a-zA-Z0-9\\s]+}/profileImage", method = RequestMethod.GET, produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] profileImage(@PathVariable("userName") final String userName) {
        if(userName==null || userName.equals("")){
            LOGGER.warn("Wrong username photo request:",new PageNotFoundException());
            throw new PageNotFoundException();
        }
        User user = us.findByUserName(userName).orElseThrow(PageNotFoundException::new);
        return user.getImage();
    }

    @RequestMapping(value = {"/profile/{userName:[a-zA-Z0-9\\s]+}","/profile/{userName:[a-zA-Z0-9\\s]+}/page/{pageNum:[0-9]+}"},method = {RequestMethod.GET})
    public ModelAndView profileInfo(Principal userDetails,
                                    @PathVariable("userName") final String userName,
                                    @PathVariable("pageNum")final Optional<Integer> pageNum,
                                    @ModelAttribute("reportReviewForm") final ReportCommentForm reportReviewForm,
                                    @ModelAttribute("reportCommentForm") final ReportCommentForm reportCommentForm,
                                    HttpServletRequest request) {
        if(userName==null || userName.equals("")){
            LOGGER.warn("Wrong username photo request:",new PageNotFoundException());
            throw new PageNotFoundException();
        }
        final ModelAndView mav = new ModelAndView("profileInfoPage");
        Optional<User> user = us.findByUserName(userName);
        mav.addObject("userProfile",userName);
        if(user.isPresent()) {
            final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
            Optional<String> quote = cs.getContentQuote(locale);
            mav.addObject("quote", quote.get());
            mav.addObject("reputationAmount",user.get().getReputation());
            mav.addObject("user", user.get());
            paginationSetup(mav,pageNum.orElse(1),rs.getAllUserReviews(user.get()));
        } else {
            LOGGER.warn("Wrong username profile request:",new PageNotFoundException());
            throw new PageNotFoundException();
        }
        mav.addObject("canPromote",false);
        if(userDetails != null) {
            String userEmail = userDetails.getName();
            User userLogged = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",userLogged.getUserName());
            mav.addObject("userId",userLogged.getId());

            rs.userLikeAndDislikeReviewsId(userLogged.getUserVotes());
            mav.addObject("userLikeReviews", rs.getUserLikeReviews());
            mav.addObject("userDislikeReviews", rs.getUserDislikeReviews());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
                mav.addObject("admin",true);
                if(!user.get().getRole().equals("admin")) {
                    mav.addObject("canPromote", true);
                }
            } else {
                mav.addObject("admin",false);
            }

        } else {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
            mav.addObject("admin",false);
            mav.addObject("userLikeReviews", rs.getUserLikeReviews());
            mav.addObject("userDislikeReviews", rs.getUserDislikeReviews());
        }
        request.getSession().setAttribute("referer","/profile/"+userName);
        return mav;
    }

    @RequestMapping(value = "/profile/{userName:[a-zA-Z0-9\\s]+}",method = {RequestMethod.POST})
    public ModelAndView profileInfo(@Valid @ModelAttribute("editProfile") final EditProfile editProfile,
                                    @PathVariable("userName") final String userName,
                                    @ModelAttribute("reportReviewForm") final ReportCommentForm reportReviewForm,
                                    @ModelAttribute("reportCommentForm") final ReportCommentForm reportCommentForm,
                                    final BindingResult errors) {
        Optional<User> user = us.findByUserName(userName);
        if(user.isPresent()) {
            us.promoteUser(user.get());
        } else {
            LOGGER.warn("Wrong username photo request:",new PageNotFoundException());
            throw new PageNotFoundException();
        }
        return new ModelAndView("redirect:/profile/" + userName);
    }


    // * ------------------------------------------------Profile Edition------------------------------------------------
    @RequestMapping(value = "/profile/editProfile", method = {RequestMethod.GET})
    public ModelAndView profileEdition(Principal userDetails,
                                       @Valid @ModelAttribute("editProfile") final EditProfile editProfile) {
        String userEmail = userDetails.getName();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        final ModelAndView mav = new ModelAndView("profileEditionPage");
        final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
        Optional<String> quote = cs.getContentQuote(locale);
        mav.addObject("quote", quote.get());
        if(user.getRole().equals("admin")){
            mav.addObject("admin",true);
        } else {
            mav.addObject("admin",false);
        }
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/profile/editProfile", method = {RequestMethod.POST})
    public ModelAndView profileEditionPost(Principal userDetails,
                                           @Valid @ModelAttribute("editProfile") final EditProfile editProfile,
                                           final BindingResult errors) throws IOException {
        String userEmail = userDetails.getName();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);

        if(Objects.equals(editProfile.getPassword(), "") && (editProfile.getProfilePicture().getSize() > 0)) {
            us.setProfilePicture(editProfile.getProfilePicture().getBytes(), user);
            return new ModelAndView("redirect:/profile");
        }

        if(errors.hasErrors()) {
            return profileEdition(userDetails,editProfile);
        }

        if(!Objects.equals(editProfile.getPassword(), "")  && ((editProfile.getProfilePicture() == null) || editProfile.getProfilePicture().getSize() <= 0 )) {
            if(!passwordEncoder.matches(editProfile.getCurrentPassword(),user.getPassword())) {
                errors.rejectValue("currentPassword","EditProfile.IncorrectOldPassword");
                return profileEdition(userDetails,editProfile);
            } else if(!editProfile.getPassword().equals(editProfile.getConfirmPassword())){
                errors.rejectValue("confirmPassword","EditProfile.NotSamePassword");
                return profileEdition(userDetails,editProfile);
            }
            us.setPassword(editProfile.getPassword(), user, "restore");
        } else if(!Objects.equals(editProfile.getPassword(), "")  && editProfile.getProfilePicture().getSize() > 0) {
            if(!passwordEncoder.matches(editProfile.getCurrentPassword(),user.getPassword())) {
                errors.rejectValue("currentPassword","EditProfile.IncorrectOldPassword");
                return profileEdition(userDetails,editProfile);
            } else if(!editProfile.getPassword().equals(editProfile.getConfirmPassword())) {
                errors.rejectValue("confirmPassword","EditProfile.NotSamePassword");
                return profileEdition(userDetails,editProfile);
            }
            us.setPassword(editProfile.getPassword(), user, "restore");
            us.setProfilePicture(editProfile.getProfilePicture().getBytes(), user);
        }

        return new ModelAndView("redirect:/profile");
    }
    // * ---------------------------------------------------------------------------------------------------------------

}
