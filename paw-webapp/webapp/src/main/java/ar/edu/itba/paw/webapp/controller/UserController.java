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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DuplicateKeyException;
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
import java.security.Principal;
import java.util.*;

@Controller
public class UserController {


    private final UserService us;
    private final ContentService cs;
    private final ReviewService rs;
    private final EmailService es;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    protected AuthenticationManager authenticationManager;
    private static final int ELEMS_AMOUNT = 5;
    @Autowired
    public UserController(final UserService us, final ContentService cs, ReviewService rs, EmailService es,AuthenticationManager authenticationManager){
        this.us = us;
        this.cs = cs;
        this.rs = rs;
        this.es = es;
        this.authenticationManager=authenticationManager;
    }

    private void HeaderSetUp(ModelAndView mav, Principal userDetails){
        if(userDetails != null) {
            String userEmail = userDetails.getName();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(user.getRole().equals("admin")){
                mav.addObject("admin",true);
            }else{
                mav.addObject("admin",false);
            }
        } else {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
            mav.addObject("admin",false);
        }
    }

    // * ----------------------------------- Login ---------------------------------------------------------------------
    @RequestMapping(value = "/login/{loginStage:sign-in}", method = {RequestMethod.GET})
    public ModelAndView loginSignIn(Principal userDetails, HttpServletRequest request, @PathVariable("loginStage") final String loginStage, @RequestParam(value = "error",required = false) Optional<Boolean> error) {
        final ModelAndView mav = new ModelAndView("logInPage");
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
    public ModelAndView LoginSingUp(Principal userDetails, @ModelAttribute("loginForm") final LoginForm loginForm, @PathVariable("loginStage") final String loginStage) {
        if(!Objects.equals(loginStage, "sign-up") && !Objects.equals(loginStage, "forgot-password") && !Objects.equals(loginStage, "sign-out")) {
            LOGGER.warn("Wrong login path:",new PageNotFoundException());
            throw new PageNotFoundException();
        }
        final ModelAndView mav = new ModelAndView("logInPage");
        mav.addObject("loginStage", loginStage);
        HeaderSetUp(mav,userDetails);
        return mav;
    }

    @RequestMapping(value = "/login/{loginStage:sign-up|forgot-password|sign-out}", method = {RequestMethod.POST})
    public ModelAndView LoginSingUp(Principal userDetails, @Valid @ModelAttribute("loginForm") final LoginForm loginForm, final BindingResult errors, RedirectAttributes redirectAttributes, @PathVariable("loginStage") final String loginStage, HttpServletRequest request) {
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
            LOGGER.info("Registrated a new user with email");

            authWithAuthManager(request,loginForm.getEmail(),loginForm.getPassword());
        } else if(Objects.equals(loginStage, "forgot-password")) {
            Optional<User> user = us.findByEmail(loginForm.getEmail());
            if(user.isPresent()) {
                us.setPassword(null, user.get(), "forgotten");
            } else {
                return LoginSingUp(userDetails, loginForm, loginStage);
            }
        } else {
            LOGGER.warn("Wrong login path:",new PageNotFoundException());
            throw new PageNotFoundException();
        }

        String referer = request.getSession().getAttribute("referer").toString();
        return new ModelAndView("redirect:" + (referer==null?"/":referer));
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
    private void paginationSetup(ModelAndView mav,int page,List<Review> reviewList){
        if(reviewList.size()==0){
            mav.addObject("reviews",reviewList);
            mav.addObject("pageSelected",1);
            mav.addObject("amountPages",1);
            return;
        }
        if(reviewList.size()>=page*ELEMS_AMOUNT)
            mav.addObject("reviews",reviewList.subList(0,page*ELEMS_AMOUNT));
        else
            mav.addObject("reviews",reviewList.subList(0, reviewList.size()));
        mav.addObject("pageSelected",page);
        mav.addObject("amountPages",Math.ceil((double)reviewList.size()/(double)ELEMS_AMOUNT));

    }

    @RequestMapping(value={"/profile","/profile/page/{pageNum:[0-9]+}"})
    public ModelAndView profile(Principal userDetails,@PathVariable("pageNum")final Optional<Integer> pageNum,HttpServletRequest request) {
        final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
        final ModelAndView mav = new ModelAndView("profileInfoPage");
        String userEmail = userDetails.getName();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        Optional<String> quote = cs.getContentQuote(locale);
        mav.addObject("quote", quote.get());
        mav.addObject("user", user);
        mav.addObject("userName",user.getUserName());
        mav.addObject("userId",user.getId());
        mav.addObject("admin",false);

        paginationSetup(mav,pageNum.orElse(1),rs.getAllUserReviews(user.getUserName()));

        request.getSession().setAttribute("referer","/profile");
        return mav;
    }

    @RequestMapping(path = "/profile/{userName:[a-zA-Z0-9\\s]+}/profileImage", method = RequestMethod.GET, produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] profileImage(@PathVariable("userName") final String userName) {
        if(userName==null || userName==""){
            LOGGER.warn("Wrong username photo request:",new PageNotFoundException());
            throw new PageNotFoundException();
        }
        User user = us.findByUserName(userName).orElseThrow(PageNotFoundException::new);
        return user.getImage();
    }

    @RequestMapping(value = {"/profile/{userName:[a-zA-Z0-9\\s]+}","/profile/{userName:[a-zA-Z0-9\\s]+}/page/{pageNum:[0-9]+}"},method = {RequestMethod.GET})
    public ModelAndView profileInfo(Principal userDetails, @PathVariable("userName") final String userName,@PathVariable("pageNum")final Optional<Integer> pageNum,HttpServletRequest request) {
        if(userName==null || userName==""){
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
            mav.addObject("user", user.get());

            paginationSetup(mav,pageNum.orElse(1),rs.getAllUserReviews(user.get().getUserName()));

        } else {
            LOGGER.warn("Wrong username profile request:",new PageNotFoundException());
            throw new PageNotFoundException();
        }

        if(userDetails != null) {
            String userEmail = userDetails.getName();
            User userLogged = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",userLogged.getUserName());
            mav.addObject("userId",userLogged.getId());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")) && !user.get().getRole().equals("admin")){
                mav.addObject("admin",true);
            }else{
                mav.addObject("admin",false);
            }

        } else {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
            mav.addObject("admin",false);
        }
        request.getSession().setAttribute("referer","/profile/"+userName);
        return mav;
    }

    @RequestMapping(value = "/profile/{userName:[a-zA-Z0-9\\s]+}",method = {RequestMethod.POST})
    public ModelAndView profileInfo(@Valid @ModelAttribute("editProfile") final EditProfile editProfile, @PathVariable("userName") final String userName,final BindingResult errors) {
        Optional<User> user = us.findByUserName(userName);
        if(user.isPresent())
            us.promoteUser(user.get());
        else {
            LOGGER.warn("Wrong username photo request:",new PageNotFoundException());
            throw new PageNotFoundException();
        }
        return new ModelAndView("redirect:/profile/" + userName);
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ------------------------------------------------Profile Edition------------------------------------------------
    @RequestMapping(value = "/profile/edit-profile", method = {RequestMethod.GET})
    public ModelAndView profileEdition(Principal userDetails, @Valid @ModelAttribute("editProfile") final EditProfile editProfile) {
        String userEmail = userDetails.getName();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        final ModelAndView mav = new ModelAndView("profileEditionPage");
        if(user.getRole().equals("admin")){
            mav.addObject("admin",true);
        }else{
            mav.addObject("admin",false);
        }
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/profile/edit-profile", method = {RequestMethod.POST})
    public ModelAndView profileEditionPost(Principal userDetails,@Valid @ModelAttribute("editProfile") final EditProfile editProfile, final BindingResult errors) throws IOException {
        if(errors.hasErrors()) {
            return profileEdition(userDetails,editProfile);
        }

        String userEmail = userDetails.getName();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        if(editProfile.getPassword() != null && ((editProfile.getProfilePicture() == null) || editProfile.getProfilePicture().getSize() <= 0 )) {
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
    public ModelAndView watchList(Principal userDetails,HttpServletRequest request) {
        String userEmail = userDetails.getName();
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
        request.getSession().setAttribute("referer","/profile/watchList");
        return mav;
    }

    @RequestMapping(value = "/watchList/add/{contentId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView watchListAddPost(Principal userDetails, @PathVariable("contentId") final Optional<Long> contentId, HttpServletRequest request) {
        if(!contentId.isPresent()) {
            LOGGER.warn("Wrong contentID:",new PageNotFoundException());
            throw new PageNotFoundException();
        }

        if(userDetails != null) {
            String userEmail = userDetails.getName();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            try {
            us.addToWatchList(user, contentId.get());}
            catch (DuplicateKeyException ignore){}
        } else {
            throw new MethodNotAllowedException();
        }

        String referer = request.getSession().getAttribute("referer").toString();
        return new ModelAndView("redirect:" + (referer==null ? "/" : referer));
    }

    @RequestMapping(value = "/watchList/delete/{contentId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView watchListDeletePost(Principal userDetails, @PathVariable("contentId") final Optional<Long> contentId, HttpServletRequest request) {
        if(!contentId.isPresent()) {
            LOGGER.warn("No content Specified:",new PageNotFoundException());
            throw new PageNotFoundException();
        }

        if(userDetails != null) {
            String userEmail = userDetails.getName();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            us.deleteFromWatchList(user, contentId.get());
        } else {
            throw new MethodNotAllowedException();
        }

        String referer = request.getSession().getAttribute("referer").toString();
        return new ModelAndView("redirect:" + (referer==null ? "/" : referer));

    }

     @RequestMapping(value = "/go/to/login", method = {RequestMethod.POST})
    public ModelAndView goToLogin()  {
        return new ModelAndView("redirect:/login/sign-in");
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ------------------------------------------------User Viewed List------------------------------------------------
    @RequestMapping(value = "/profile/viewedList", method = {RequestMethod.GET})
    public ModelAndView viewedList(Principal userDetails,HttpServletRequest request) {
        String userEmail = userDetails.getName();
        final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        List<Content> viewedListContent = us.getUserViewedList(user);
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
        request.getSession().setAttribute("referer","/profile/viewedList");
        return mav;
    }

    @RequestMapping(value = "/viewedList/add/{contentId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView viewedListAddPost(Principal userDetails, @PathVariable("contentId") final Optional<Long> contentId, HttpServletRequest request) {
        if(!contentId.isPresent()) {
            throw new PageNotFoundException();
        }
        String userEmail = userDetails.getName();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        try{
        us.addToViewedList(user, contentId.get());}
        catch (DuplicateKeyException ignore){}
        String referer = request.getSession().getAttribute("referer").toString();
        return new ModelAndView("redirect:" + (referer==null?"/":referer));
    }

    @RequestMapping(value = "/viewedList/delete/{contentId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView viewedListDeletePost(Principal userDetails, @PathVariable("contentId") final Optional<Long> contentId, HttpServletRequest request) {
        if(!contentId.isPresent()) {
            throw new PageNotFoundException();
        }
        String userEmail = userDetails.getName();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        us.deleteFromViewedList(user, contentId.get());
        String referer = request.getSession().getAttribute("referer").toString();
        return new ModelAndView("redirect:" + (referer==null?"/":referer));
    }
    // * ---------------------------------------------------------------------------------------------------------------
}
