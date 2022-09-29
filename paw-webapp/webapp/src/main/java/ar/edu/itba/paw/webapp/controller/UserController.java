package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Content;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Controller
public class UserController {


    private final UserService us;
    private final ContentService cs;
    private final ReviewService rs;
    private final EmailService es;

    protected AuthenticationManager authenticationManager;

    @Autowired
    public UserController(final UserService us, final ContentService cs, ReviewService rs, EmailService es,AuthenticationManager authenticationManager){
        this.us = us;
        this.cs = cs;
        this.rs = rs;
        this.es = es;
        this.authenticationManager=authenticationManager;
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

    // * ----------------------------------- Login ---------------------------------------------------------------------
    @RequestMapping(value = "/login/{loginStage:sign-in}", method = {RequestMethod.GET})
    public ModelAndView loginSignIn(@AuthenticationPrincipal PawUserDetails userDetails, HttpServletRequest request, @PathVariable("loginStage") final String loginStage, @RequestParam(value = "error",required = false) Optional<Boolean> error) {
        final ModelAndView mav = new ModelAndView("logInPage");
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("url_prior_login", referrer);
        mav.addObject("loginStage", loginStage);
        HeaderSetUp(mav,userDetails);
        if(error.isPresent()){
            mav.addObject("error",true);
        }else{
            mav.addObject("error",false);
        }
        return mav;
    }


    @RequestMapping(value = "/login/{loginStage:sign-up|forgot-password}", method = {RequestMethod.GET})
    public ModelAndView LoginSingUp(@AuthenticationPrincipal PawUserDetails userDetails, @ModelAttribute("loginForm") final LoginForm loginForm, @PathVariable("loginStage") final String loginStage) {
        if(!Objects.equals(loginStage, "sign-up") && !Objects.equals(loginStage, "forgot-password") && !Objects.equals(loginStage, "sign-out")) {
            throw new PageNotFoundException();
        }
        final ModelAndView mav = new ModelAndView("logInPage");
        mav.addObject("loginStage", loginStage);
        HeaderSetUp(mav,userDetails);
        return mav;
    }

    @RequestMapping(value = "/login/{loginStage:sign-up|forgot-password|sign-out}", method = {RequestMethod.POST})
    public ModelAndView LoginSingUp(@AuthenticationPrincipal PawUserDetails userDetails, @Valid @ModelAttribute("loginForm") final LoginForm loginForm, final BindingResult errors, RedirectAttributes redirectAttributes, @PathVariable("loginStage") final String loginStage, HttpServletRequest request) {
        if(errors.hasErrors()) {
            return LoginSingUp(userDetails, loginForm, loginStage);
        }
        if(Objects.equals(loginStage, "sign-up")) {
            Optional<User> existUserEmail = us.findByEmail(loginForm.getEmail());
            Optional<User> existUserName = us.findByUserName(loginForm.getUsername());
            if(existUserEmail.isPresent() || existUserName.isPresent()) {
                return LoginSingUp(userDetails, loginForm, loginStage);
            }
            User newUser = new User(null, loginForm.getEmail(), loginForm.getUsername(), loginForm.getPassword(), 0L, null,"user");
            us.register(newUser);
            authWithAuthManager(request,loginForm.getEmail(),loginForm.getPassword());
        } else if(Objects.equals(loginStage, "forgot-password")) {
            Optional<User> user = us.findByEmail(loginForm.getEmail());
            if(user.isPresent()) {
                us.setPassword(null, user.get(), "forgotten");
            } else {
                return LoginSingUp(userDetails, loginForm, loginStage);
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
    // * ---------------------------------------------------------------------------------------------------------------


    // * ------------------------------------------------Profile View --------------------------------------------------
    //This path is for the logged in profile, if the person is not logged in it will fail
    @RequestMapping("/profile")
    public ModelAndView profile(@AuthenticationPrincipal PawUserDetails userDetails) {
        final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
        final ModelAndView mav = new ModelAndView("profileInfoPage");
        String userEmail = userDetails.getUsername();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        Optional<String> quote = cs.getContentQuote(locale);
        mav.addObject("quote", quote.get());
        mav.addObject("user", user);
        mav.addObject("reviews",rs.getAllUserReviews(user.getUserName()));
        mav.addObject("userName",user.getUserName());
        mav.addObject("userId",user.getId());
        mav.addObject("admin",false);

        return mav;
    }

    @RequestMapping(path = "/profile/{userName:[a-zA-Z0-9\\s]+}/profileImage", method = RequestMethod.GET, produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] profileImage(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("userName") final String userName) {
        if(userName==null || userName==""){
            throw new PageNotFoundException();
        }
        User user = us.findByUserName(userName).orElseThrow(PageNotFoundException::new);
        return user.getImage();
    }

    @RequestMapping("/profile/{userName:[a-zA-Z0-9\\s]+}")
    public ModelAndView profileInfo(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("userName") final String userName) {
        if(userName==null || userName==""){
            throw new PageNotFoundException();
        }
        final ModelAndView mav = new ModelAndView("profileInfoPage");
        Optional<User> user = us.findByUserName(userName);
        if(user.isPresent()) {
            final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
            Optional<String> quote = cs.getContentQuote(locale);
            mav.addObject("quote", quote.get());
            mav.addObject("user", user.get());
            mav.addObject("reviews",rs.getAllUserReviews(user.get().getUserName()));
        } else {
            throw new PageNotFoundException();
        }

        try {
            String userEmail = userDetails.getUsername();
            User userLogged = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",userLogged.getUserName());
            mav.addObject("userId",userLogged.getId());
            if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) && !user.get().getRole().equals("admin")){
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

    @RequestMapping(value = "/profile/{userName:[a-zA-Z0-9\\s]+}",method = {RequestMethod.POST})
    public ModelAndView profileInfo(@AuthenticationPrincipal PawUserDetails userDetails,@Valid @ModelAttribute("editProfile") final EditProfile editProfile, @PathVariable("userName") final String userName,final BindingResult errors) {
        Optional<User> user = us.findByUserName(userName);
        if(user.isPresent())
            us.promoteUser(user.get().getId());
        else throw new PageNotFoundException();
        return new ModelAndView("redirect:/profile/" + userName);
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ------------------------------------------------Profile Edition------------------------------------------------
    @RequestMapping(value = "/profile/edit-profile", method = {RequestMethod.GET})
    public ModelAndView profileEdition(@AuthenticationPrincipal PawUserDetails userDetails, @Valid @ModelAttribute("editProfile") final EditProfile editProfile) {
        String userEmail = userDetails.getUsername();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        final ModelAndView mav = new ModelAndView("profileEditionPage");
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/profile/edit-profile", method = {RequestMethod.POST})
    public ModelAndView profileEditionPost(@AuthenticationPrincipal PawUserDetails userDetails,@Valid @ModelAttribute("editProfile") final EditProfile editProfile, final BindingResult errors) throws IOException {
        if(errors.hasErrors()) {
            return profileEdition(userDetails,editProfile);
        }

        String userEmail = userDetails.getUsername();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        if(editProfile.getPassword() != null && (editProfile.getProfilePicture() == null) || editProfile.getProfilePicture().getSize() <= 0 ) {
            us.setPassword(editProfile.getPassword(), user, "restore");
        } else if(editProfile.getPassword() == null && (editProfile.getProfilePicture().getSize() > 0)) {
            us.setProfilePicture(editProfile.getProfilePicture().getBytes(), user);
        } else if(editProfile.getPassword() != null && editProfile.getProfilePicture().getSize() > 0) {
            us.setPassword(editProfile.getPassword(), user, "restore");
            us.setProfilePicture(editProfile.getProfilePicture().getBytes(), user);
        }

        return new ModelAndView("redirect:/profile");
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ------------------------------------------------User Watch List------------------------------------------------
    @RequestMapping(value = "/profile/watchList", method = {RequestMethod.GET})
    public ModelAndView watchList(@AuthenticationPrincipal PawUserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        List<Content> watchListContent = us.getWatchList(user);
        List<Long> userWatchListContentId = us.getUserWatchListContent(user);
        final ModelAndView mav = new ModelAndView("watchListPage");
        Optional<String> quote = cs.getContentQuote(locale);
        mav.addObject("quote", quote.get());
        mav.addObject("user", user);
        mav.addObject("userName", user.getUserName());
        mav.addObject("userId", user.getId());
        mav.addObject("watchListContent", watchListContent);
        mav.addObject("watchListSize", watchListContent.size());
        mav.addObject("userWatchListContentId", userWatchListContentId);

        if (user.getRole().equals("admin")) {
            mav.addObject("admin", true);
        } else {
            mav.addObject("admin", false);
        }
        return mav;
    }

    @RequestMapping(value = "/watchList/add/{contentId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView watchListAddPost(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("contentId") final Optional<Long> contentId, HttpServletRequest request) {
        if(!contentId.isPresent()) {
            throw new PageNotFoundException();
        }
        String userEmail = userDetails.getUsername();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        us.addToWatchList(user, contentId.get());
        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:" + referer);
    }

    @RequestMapping(value = "/watchList/delete/{contentId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView watchListDeletePost(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("contentId") final Optional<Long> contentId, HttpServletRequest request) {
        if(!contentId.isPresent()) {
            throw new PageNotFoundException();
        }
        String userEmail = userDetails.getUsername();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        us.deleteFromWatchList(user, contentId.get());
        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:" + referer);
    }

     @RequestMapping(value = "/go/to/login", method = {RequestMethod.POST})
    public ModelAndView goToLogin()  {
        return new ModelAndView("redirect:/login/sign-in");
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ------------------------------------------------User Viewed List------------------------------------------------
    @RequestMapping(value = "/profile/viewedList", method = {RequestMethod.GET})
    public ModelAndView viewedList(@AuthenticationPrincipal PawUserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        List<Content> viewedListContent = us.getUserViewedList(user);
//        List<Long> viewedListContentId = us.getUserViewedListContent(user);
        List<Long> userWatchListContentId = us.getUserWatchListContent(user);
        final ModelAndView mav = new ModelAndView("viewedListPage");
        Optional<String> quote = cs.getContentQuote(locale);
        mav.addObject("quote", quote.get());
        mav.addObject("user", user);
        mav.addObject("userName", user.getUserName());
        mav.addObject("userId", user.getId());
        mav.addObject("viewedListContent", viewedListContent);
        mav.addObject("viewedListContentSize", viewedListContent.size());
        mav.addObject("userWatchListContentId", userWatchListContentId);

        if (user.getRole().equals("admin")) {
            mav.addObject("admin", true);
        } else {
            mav.addObject("admin", false);
        }
        return mav;
    }

    @RequestMapping(value = "/viewedList/add/{contentId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView viewedListAddPost(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("contentId") final Optional<Long> contentId, HttpServletRequest request) {
        if(!contentId.isPresent()) {
            throw new PageNotFoundException();
        }
        String userEmail = userDetails.getUsername();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        us.addToViewedList(user, contentId.get());
        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:" + referer);
    }

    @RequestMapping(value = "/viewedList/delete/{contentId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView viewedListDeletePost(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("contentId") final Optional<Long> contentId, HttpServletRequest request) {
        if(!contentId.isPresent()) {
            throw new PageNotFoundException();
        }
        String userEmail = userDetails.getUsername();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        us.deleteFromViewedList(user, contentId.get());
        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:" + referer);
    }
    // * ---------------------------------------------------------------------------------------------------------------
}
