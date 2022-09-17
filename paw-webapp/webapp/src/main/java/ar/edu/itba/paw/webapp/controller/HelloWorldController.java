package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Content;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;

import ar.edu.itba.paw.services.EmailService;
import ar.edu.itba.paw.services.ReviewService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.auth.PawUserDetails;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.EditProfile;
import ar.edu.itba.paw.webapp.form.LoginForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class HelloWorldController {

    private final UserService us;
    private final ContentService cs;
    private final ReviewService rs;
    private final EmailService es;
    private final int ELEMS_AMOUNT = 10;

    @Autowired  //Para indicarle que este es el constructor que quiero que use
    public HelloWorldController(final UserService us, final ContentService cs, ReviewService rs, EmailService es){
        this.us = us;
        this.cs = cs;
        this.rs = rs;
        this.es = es;
    }

    // * ----------------------------------- Movie and Series Info -----------------------------------------------------------------------
    @RequestMapping(value= {"/","page/{pageNum}"})
    public ModelAndView helloWorld(@PathVariable("pageNum")final Optional<Integer> pageNum,@RequestParam(name = "query", defaultValue = "") final String query) {
        final ModelAndView mav = new ModelAndView("ContentPage");
        mav.addObject("query", query);
        List<Content> contentList = cs.getSearchedContent(query);
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

        }
        return mav;
    }


    @RequestMapping(value= {"/{type:movies|series}","/{type:movies|series}/page/{pageNum}"})
    public ModelAndView contentType(@PathVariable("type") final String type,@PathVariable("pageNum")final Optional<Integer> pageNum) {
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
            mav.addObject("amountPages",(int)Math.ceil((double) contentList.size()/(double)ELEMS_AMOUNT));
            mav.addObject("pageSelected",page);
            mav.addObject("contentType", type);
            mav.addObject("genre","ANY");
            mav.addObject("durationFrom","ANY");
            mav.addObject("durationTo","ANY");
        }
        return mav;
    }

    @RequestMapping(value = {"/search", "/page/{pageNum}"})
    public ModelAndView search(@PathVariable("pageNum")final Optional<Integer> pageNum, @RequestParam(name = "query", defaultValue = "") final String query) {
        final ModelAndView mav = new ModelAndView("ContentPage");
        mav.addObject("query", query);
        List<Content> contentList = cs.getSearchedContent(query);
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
            mav.addObject("allContent", contentList);
        }
        return mav;
    }

    @RequestMapping("/{type:movie|serie}/{contentId:[0-9]+}")
    public ModelAndView reviews(@PathVariable("contentId")final long contentId, @PathVariable("type") final String type) {
        final ModelAndView mav = new ModelAndView("infoPage");
        mav.addObject("details", cs.findById(contentId).orElseThrow(PageNotFoundException::new));
        List<Review> reviewList = rs.getAllReviews(contentId);
        if(reviewList == null) {
            throw new PageNotFoundException();
        } else {
            mav.addObject("reviews", reviewList);
        }
        return mav;
    }

    // * -------------------------------------------------------------------------------------------------------------------------------------


    // *  ----------------------------------- Movies and Serie Filters -----------------------------------------------------------------------

    @RequestMapping(value = {"/{type:movies|series}/filters" , "/{type:movies|series}/filters/page/{pageNum}"})
    public ModelAndView moviesWithFilters(
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
        return mav;
    }

    // * -----------------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Movies and Series Review -----------------------------------------------------------------------
    @RequestMapping(value = "/reviewForm/{type:movie|serie}/{id:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView reviewFormCreate(@ModelAttribute("registerForm") final ReviewForm reviewForm, @PathVariable("id")final long id) {
        final ModelAndView mav = new ModelAndView("reviewRegistration");
        mav.addObject("details", cs.findById(id).orElseThrow(PageNotFoundException::new));
        return mav;
    }

    @RequestMapping(value = "/reviewForm/{type:movie|serie}/{id:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView reviewFormMovie(@Valid @ModelAttribute("registerForm") final ReviewForm form, final BindingResult errors, @PathVariable("id")final long id, @PathVariable("type")final String type) {
        if(errors.hasErrors()) {
            return reviewFormCreate(form,id);
        }
//        TODO: CAMBIAR LA LOGICA DE ESTO ESTO
        User newUser = new User(null, form.getEmail(), form.getUserName(),"CAMBIAR",(long)4);//ESTO SE TIENE QUE SACAR
        //Primero intenta agregar el usuario, luego intenta agregar la review
        Optional<Long> userId = us.register(newUser);
        // TODO: Falta Agregar mensaje de error para el caso -1 (si falla en el pedido)
        if(userId.get() == -1) {
            ModelAndView mav=reviewFormCreate(form,id);
            mav.addObject("errorMsg","This username or mail is already in use.");
            return mav;
        }
        try {
//            TODO: Cambiar esto tambien
            // ReviewId va en null porque eso lo asigna la tabla
            Review newReview = new Review(null, type, id, form.getName(), form.getDescription(), form.getRating(), form.getUserName(),null);
            newReview.setId(id);
            rs.addReview(newReview);
        }
        catch(DuplicateKeyException e){
            ModelAndView mav = reviewFormCreate(form,id);
            mav.addObject("errorMsg","You have already written a review for this " + type + ".");
            return mav;
        }

        ModelMap model =new ModelMap();
        return new ModelAndView("redirect:/" + type + "/" + id, model);
    }

    // * -----------------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- login Page -----------------------------------------------------------------------
    @RequestMapping(value = "/login/{loginStage:sign-in}", method = {RequestMethod.GET})
    public ModelAndView emailForm(@PathVariable("loginStage") final String loginStage) {
        final ModelAndView mav = new ModelAndView("logInPage");
        mav.addObject("loginStage", loginStage);
        return mav;
    }


//    TODO: Terminar
    @RequestMapping(value = "/login/{loginStage:sign-up|forgot-password|sign-out}", method = {RequestMethod.GET})
    public ModelAndView emailForm(@ModelAttribute("loginForm") final LoginForm loginForm,@PathVariable("loginStage") final String loginStage) {
        final ModelAndView mav = new ModelAndView("logInPage");
        mav.addObject("loginStage", loginStage);
        return mav;
    }

    @RequestMapping(value = "/login/{loginStage:sign-up|forgot-password|sign-out}", method = {RequestMethod.POST})
    public ModelAndView emailFormVerification(@Valid @ModelAttribute("loginForm") final LoginForm loginForm, final BindingResult errors, RedirectAttributes redirectAttributes, @PathVariable("loginStage") final String loginStage) {
        if(errors.hasErrors()) {
            return emailForm(loginForm, loginStage);
        }
        if(Objects.equals(loginStage, "sign-up")) {
            User newUser = new User(null, loginForm.getEmail(), loginForm.getUserName(), loginForm.getPassword(), 0L);
            us.register(newUser);
            es.sendRegistrationEmail(newUser);
        } else if(Objects.equals(loginStage, "forgot-password")) {
//            TODO, VER EL TEMA DE MANDAR EL EMAIL
            es.sendForgottenPasswordEmail(loginForm.getEmail());
        } else if (Objects.equals(loginStage, "set-password")) {
            // TODO: VER EL TEMA DE SETEAR NUEVA PASSWORD EN BDD
            User newUser = new User(null, loginForm.getEmail(), loginForm.getUserName(), loginForm.getPassword(), 0L);
//            us.register(newUser);
        }

        return new ModelAndView("redirect:/login/sign-in");

    }

    // * -----------------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Errors Page -----------------------------------------------------------------------
    @ExceptionHandler(PageNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView PageNotFound(){
        return new ModelAndView("errors");
    }
    // * -----------------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Otros -----------------------------------------------------------------------
//    @RequestMapping("/register")
//    public ModelAndView register(
//            @RequestParam(value = "email", required = false) final String email,
//            @RequestParam("password") final String password
//    ) {
//        final User user = us.register(email, password);
//        return new ModelAndView("redirect:/profile" + user.getId());
//    }
//
    // * ------------------------------------------------Profile--------------------------------------------------------------------
    @RequestMapping("/hola/{userId:[0-9]+}")
    public ModelAndView profile(@AuthenticationPrincipal PawUserDetails userDetails,@PathVariable("userId") final long userId) {
        final ModelAndView mav = new ModelAndView("profilePage");
//        String userEmail = userDetails.getUsername();
//        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
//        mav.addObject("user", user);
//        mav.addObject("reviews",rs.getAllUserReviews(user.getUserName()));
        return mav;
    }

    @RequestMapping(value = "/hola/{userId:[0-9]+}/edit-profile", method = {RequestMethod.GET})
    public ModelAndView profileEdition(@AuthenticationPrincipal PawUserDetails userDetails, @Valid @ModelAttribute("editProfile") final EditProfile editProfile, @PathVariable("userId") final long userId) {
//        String userEmail = userDetails.getUsername();
//        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        final ModelAndView mav = new ModelAndView("profileEditionPage");
//        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/hola/{userId:[0-9]+}/edit-profile", method = {RequestMethod.POST})
    public ModelAndView profileEditionPost(@AuthenticationPrincipal PawUserDetails userDetails,@Valid @ModelAttribute("editProfile") final EditProfile editProfile, final BindingResult errors, @PathVariable("userId") final long userId)  {

        if(errors.hasErrors()) {
            return profileEdition(userDetails,editProfile, userId);
        }

        User user = us.findById(userId).orElseThrow(PageNotFoundException::new);
        us.setPassword(editProfile.getPassword(), user);

        return new ModelAndView("redirect:/hola/" + userId);
    }

    // * -----------------------------------------------------------------------------------------------------------------------

}

