package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.ForgotPasswordForm;
import ar.edu.itba.paw.webapp.form.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.Document;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;
import java.util.Optional;


@Controller
public class LoginController {
    private final UserService us;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    protected AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(final UserService us, AuthenticationManager authenticationManager) {
        this.us = us;
        this.authenticationManager = authenticationManager;
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
        mav.addObject("type","all");
    }

    // * ----------------------------------- Login ---------------------------------------------------------------------
    @RequestMapping(value = "/login/{loginStage:sign-in}", method = {RequestMethod.GET})
    public ModelAndView loginSignIn(Principal userDetails,
                                    @PathVariable("loginStage") final String loginStage,
                                    Optional<Boolean> forgotPasswordBoolean,
                                    @RequestParam(value = "error",required = false) Optional<Boolean> error) {
        final ModelAndView mav = new ModelAndView("logInPage");
        mav.addObject("loginStage", loginStage);
        mav.addObject("forgotPasswordBoolean", forgotPasswordBoolean.isPresent());
        HeaderSetUp(mav,userDetails);
        if(error.isPresent()){
            mav.addObject("error",true);
        } else {
            mav.addObject("error",false);
        }
        return mav;
    }

    @RequestMapping(value = "/login/forgot-password", method = {RequestMethod.GET})
    public ModelAndView LoginForgotPassword(Principal userDetails,
                                            @ModelAttribute("forgotPasswordForm") final ForgotPasswordForm loginForm) {
        final ModelAndView mav = new ModelAndView("logInPage");
        mav.addObject("loginStage", "forgot-password");
        HeaderSetUp(mav,userDetails);
        return mav;
    }

    @RequestMapping(value = "/login/forgot-password", method = {RequestMethod.POST})
    public ModelAndView LoginForgotPassword(Principal userDetails,
                                            @Valid @ModelAttribute("forgotPasswordForm") final ForgotPasswordForm forgotPasswordForm,
                                            final BindingResult errors) {
        if(errors.hasErrors()) {
            return LoginForgotPassword(userDetails, forgotPasswordForm);
        }

        Optional<User> user = us.findByEmail(forgotPasswordForm.getEmail());
        if(user.isPresent()) {
            us.setPassword(null, user.get(), "forgotten");
        } else {
            return LoginForgotPassword(userDetails, forgotPasswordForm);
        }

        ModelAndView mav = new ModelAndView("redirect:" + "/login/sign-in");
        mav.addObject("forgotPasswordBoolean", true);
        return mav;
    }

    @RequestMapping(value = "/login/{loginStage:sign-up}", method = {RequestMethod.GET})
    public ModelAndView LoginSingUp(Principal userDetails,
                                    @ModelAttribute("loginForm") final LoginForm loginForm,
                                    @PathVariable("loginStage") final String loginStage) {
        if(!Objects.equals(loginStage, "sign-up") && !Objects.equals(loginStage, "forgot-password") && !Objects.equals(loginStage, "sign-out")) {
            LOGGER.warn("Wrong login path:",new PageNotFoundException());
            throw new PageNotFoundException();
        }
        final ModelAndView mav = new ModelAndView("logInPage");
        mav.addObject("loginStage", loginStage);
        HeaderSetUp(mav,userDetails);
        return mav;
    }

    @RequestMapping(value = "/login/{loginStage:sign-up|sign-out}", method = {RequestMethod.POST})
    public ModelAndView LoginSingUp(Principal userDetails,
                                    @Valid @ModelAttribute("loginForm") final LoginForm loginForm,
                                    final BindingResult errors,
                                    @PathVariable("loginStage") final String loginStage,
                                    HttpServletRequest request) {
        if(errors.hasErrors()) {
            return LoginSingUp(userDetails, loginForm, loginStage);
        }
        if(Objects.equals(loginStage, "sign-up")) {
            if(!Objects.equals(loginForm.getPassword(), loginForm.getConfirmPassword())) {
                errors.rejectValue("confirmPassword","EditProfile.NotSamePassword");
                return LoginSingUp(userDetails, loginForm, loginStage);
            }
            us.register( loginForm.getEmail(), loginForm.getUsername(), loginForm.getPassword());
            LOGGER.info("Registered a new user with email");
            us.authWithAuthManager(request, loginForm.getEmail(), loginForm.getPassword(), authenticationManager);
        } else {
            LOGGER.warn("Wrong login path:",new PageNotFoundException());
            throw new PageNotFoundException();
        }

        String referer = request.getSession().getAttribute("referer").toString();
        return new ModelAndView("redirect:" + (referer==null?"/":referer));
    }

    // * ---------------------------------------------------------------------------------------------------------------

}