package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Content;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;

import ar.edu.itba.paw.services.ReviewService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.LoginForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Controller
public class HelloWorldController {

    private final UserService us;
    private final ContentService cs;
    private final ReviewService rs;
    private final int ELEMS_AMOUNT = 10;

    @Autowired  //Para indicarle que este es el constructor que quiero que use
    public HelloWorldController(final UserService us, final ContentService cs, ReviewService rs){
        this.us = us;
        this.cs = cs;
        this.rs = rs;
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
            mav.addObject("allContent", contentList.subList((page-1)*ELEMS_AMOUNT,(page-1)*ELEMS_AMOUNT + ELEMS_AMOUNT));
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
        List<Content> contentList = cs.getAllContent(auxType);
        if( contentList == null) {
            throw new PageNotFoundException();
        } else {
            mav.addObject("allContent", contentList.subList((page-1)*ELEMS_AMOUNT,(page-1)*ELEMS_AMOUNT + ELEMS_AMOUNT));
            mav.addObject("amountPages",(int)Math.ceil((double) contentList.size()/(double)ELEMS_AMOUNT));
            mav.addObject("pageSelected",page);
            mav.addObject("contentType", type);
            mav.addObject("genre","ANY");
            mav.addObject("durationFrom","ANY");
            mav.addObject("durationTo","ANY");
        }
        return mav;
    }

//    @RequestMapping("/search")
//    public ModelAndView search(@RequestParam(name = "query", defaultValue = "") final String query) {
//        final ModelAndView mav = new ModelAndView("index");
//        mav.addObject("query", query);
//        List<Content> movieList = cs.getSearchedMovies(query);
//        if(movieList == null && seriesList == null) {
//            throw new PageNotFoundException();
//
//        } else if(movieList != null && seriesList == null) {
//            mav.addObject("movies", movieList);
//
//        }else if(movieList == null) {
//            mav.addObject("movies", seriesList);
//        }
//        return mav;
//    }

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
            contentListFilter = cs.findByGenre(auxType, genre) ;
        } else if(Objects.equals(genre, "ANY") && !Objects.equals(durationFrom, "ANY")) {
            contentListFilter = cs.findByDuration(auxType, Integer.parseInt(durationFrom), Integer.parseInt(durationTo));
        } else if(!Objects.equals(durationFrom, "ANY") && !Objects.equals(genre, "ANY")) {    // Caso de que si los filtros estan vacios
            contentListFilter = cs.findByDurationAndGenre(auxType, genre, Integer.parseInt(durationFrom), Integer.parseInt(durationTo));
        } else{
            contentListFilter = cs.getAllContent(auxType);
        }

        if(contentListFilter == null) {
            throw new PageNotFoundException();
        } else {
            mav.addObject("allContent", contentListFilter.subList((page-1)*ELEMS_AMOUNT,(page-1)*ELEMS_AMOUNT + ELEMS_AMOUNT));
            mav.addObject("amountPages",Math.ceil((double)contentListFilter.size()/(double)ELEMS_AMOUNT));
            mav.addObject("pageSelected",page);
            mav.addObject("contentType", type);

        }
        return mav;
    }

    // * -----------------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Movies and Series Review -----------------------------------------------------------------------
//    @RequestMapping(value = "/reviewForm/{type:movie|serie}/{id:[0-9]+}", method = {RequestMethod.GET})
//    public ModelAndView reviewFormCreate(@ModelAttribute("registerForm") final ReviewForm reviewForm, @PathVariable("id")final long id) {
//        final ModelAndView mav = new ModelAndView("reviewRegistration");
//        mav.addObject("details", cs.findById(id).orElseThrow(PageNotFoundException::new));
//        return mav;
//    }
//
//    @RequestMapping(value = "/reviewForm/{type:movie|serie}/{id:[0-9]+}", method = {RequestMethod.POST})
//    public ModelAndView reviewFormMovie(@Valid @ModelAttribute("registerForm") final ReviewForm form, final BindingResult errors, @PathVariable("id")final long id, @PathVariable("type")final String type) {
//        if(errors.hasErrors()) {
//            return reviewFormCreate(form,id);
//        }
//        User newUser = new User(null, form.getEmail(), form.getUserName());
//        //Primero intenta agregar el usuario, luego intenta agregar la review
//        Optional<Long> userId = us.register(newUser);
//        //Falta Agregar mensaje de error para el caso -1 (si falla en el pedido)
//        if(userId.get() == -1) {
//            ModelAndView mav=reviewFormCreate(form,id);
//            mav.addObject("errorMsg","This username or mail is already in use.");
//            return mav;
//        }
//        try {
//            // ReviewId va en null porque eso lo asigna la tabla
//            Review newReview = new Review(null, type, id, userId.get(), form.getName(), form.getDescription(), form.getRating());
//            newReview.setId(id);
//            rs.addReview(newReview);
//        }
//        catch(DuplicateKeyException e){
//            ModelAndView mav = reviewFormCreate(form,id);
//            mav.addObject("errorMsg","You have already written a review for this " + type + ".");
//            return mav;
//        }
//
//        ModelMap model =new ModelMap();
//        model.addAttribute("toastMsg","Your review was correctly added");
//        return new ModelAndView("redirect:/" + type + "/" + id, model);
//    }
    // * -----------------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- login Page -----------------------------------------------------------------------

//    TODO: Terminar
    @RequestMapping(value = "/login/{loginStage:verifyEmail|log|registration|setPassword}", method = {RequestMethod.GET})
    public ModelAndView emailForm(@ModelAttribute("loginForm") final LoginForm loginForm, @PathVariable("loginStage") final String loginStage) {
        final ModelAndView mav = new ModelAndView("logInPage");
//        * Pregunto que tipo de loginStage es
        if(Objects.equals(loginStage, "verifyEmail") || Objects.equals(loginStage, "log") || Objects.equals(loginStage, "registration") || Objects.equals(loginStage, "setPassword")) {
//            * Si es serPassword o log (login)
            if(Objects.equals(loginStage, "setPassword") || Objects.equals(loginStage, "log")) {
                if(loginForm.getEmail() != null) {
//                * Me traigo la info del ususario ese
                    User registedUserInfo = us.findByEmail(loginForm.getEmail()).get();
//                * Si la contrasena es 1, significa que no esta seteada => la tiene que setear y lo mando a que la setee
                    if(Objects.equals(registedUserInfo.getPassword(), "1")) {
                        mav.addObject("loginStage", "setPassword");
                    } else {
//                    * Si es distinto de 1 significa que ya la tiene seteada => lo mando a que se loguee
                        mav.addObject("loginStage", "log");
                    }
                    mav.addObject("userEmail", registedUserInfo.getEmail());
                    mav.addObject("userName", registedUserInfo.getUserName());
                    return mav;
                } else {
                    return new ModelAndView("redirect:/login/verifyEmail");
                }
//                * Ahora pregunto si es registration
            } else if(Objects.equals(loginStage, "registration")) {
//                * Si es disitnto de null, me lo guardo y lo paso asi no lo tineen que volver a escribir
                if(loginForm.getEmail() != null) {
                    mav.addObject("userEmail", loginForm.getEmail());
                    mav.addObject("loginStage", loginStage);
                } else {
//                    * SI es null, significa que intentaron entrar cambiando la URL, enotnces lo redirijo
                    return new ModelAndView("redirect:/login/verifyEmail");
                }
                return mav;
            } else {
                mav.addObject("loginStage", loginStage);
                return mav;
            }
        } else {
            throw new PageNotFoundException();
        }
    }

    @RequestMapping(value = "/login/{loginStage:verifyEmail|log|registration|setPassword}", method = {RequestMethod.POST})
    public ModelAndView emailFormVerification(@Valid @ModelAttribute("loginForm") final LoginForm loginForm, final BindingResult errors, RedirectAttributes redirectAttributes, @PathVariable("loginStage") final String loginStage) {
        if(errors.hasErrors()) {
            return emailForm(loginForm, loginStage);
        }
        if(Objects.equals(loginStage, "verifyEmail")) {
            try {
                User registedUserInfo = us.findByEmail(loginForm.getEmail()).get();
                final ModelAndView mav;
                if(Objects.equals(registedUserInfo.getPassword(), "1")) {
//                    TODO: VER SI ESTO ANDA ASI, LA DUDA SERIA DSI SE VA A MANDAR ESTA INFO CUANDO HAGA EL REDIRECTO O NO YA QUE ARRIBA DEFINO OTRO MAV (EN LA FUNCION DE ARRIBA)
                    return emailForm(loginForm, "setPassword");

                } else {
//                    TODO: VER SI ESTO ANDA ASI, LA DUDA SERIA DSI SE VA A MANDAR ESTA INFO CUANDO HAGA EL REDIRECTO O NO YA QUE ARRIBA DEFINO OTRO MAV (EN LA FUNCION DE ARRIBA)
                    return emailForm(loginForm, "log");
                }
//                mav.addObject("userEmail", registedUserInfo.getEmail());
//                mav.addObject("userName", registedUserInfo.getUserName());
            } catch (NoSuchElementException e) {
                return emailForm(loginForm, "registration");
            }
        } else if(Objects.equals(loginStage, "setPassword")) {
            us.setPassword(loginForm.getPassword(), loginForm.getEmail());
        } else if(Objects.equals(loginStage, "registration")) {
            User newUser = new User(null, loginForm.getEmail(), loginForm.getUserName(), loginForm.getPassword(), 0L);
            us.register(newUser);
        } else if(Objects.equals(loginStage, "log")) {
            us.setPassword(loginForm.getPassword(), loginForm.getEmail());
        }

        return new ModelAndView("redirect:/");

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
//    @RequestMapping("/profile/{userId:[0-9]+}")
//    public ModelAndView profile(@PathVariable("userId") final long userId) {
//        final ModelAndView mav = new ModelAndView("profile");
//        mav.addObject("user", us.findById(userId).orElseThrow(UserNotFoundException::new));
//        return mav;
//    }
    // * -----------------------------------------------------------------------------------------------------------------------


}

