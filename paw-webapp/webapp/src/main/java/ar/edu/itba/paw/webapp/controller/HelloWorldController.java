package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Movie;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Serie;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.MovieService;
import ar.edu.itba.paw.services.SerieService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HelloWorldController {

    private final UserService us;
    private final MovieService ms;
    private final SerieService ss;
//    private final ReviewService rs;

    @Autowired  //Para indicarle que este es el constructor que quiero que use
    public HelloWorldController(final UserService us, final MovieService ms, SerieService ss){
        this.us = us;
        this.ms = ms;
        this.ss = ss;
    }

//    @RequestMapping(value = "/", method = RequestMethod.GET, headers = ..., consumes = ..., produces = ...)
    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("index");
        List<Movie> movieList = ms.getAllMovies();
        if( movieList == null) {
            throw new UserNotFoundException();
        } else {
            mav.addObject("movies", movieList);
        }
        return mav;
    }

//    TODO: Ver como transformar la para que la root sea /movies
//    @RequestMapping("/movies")
//    public ModelAndView movies() {
//        final ModelAndView mav = new ModelAndView("index");
//        mav.addObject("movies", ms.getAllMovies().orElseThrow(UserNotFoundException::new));
//        return mav;
//    }

    @RequestMapping("/series")
    public ModelAndView series() {
        final ModelAndView mav = new ModelAndView("seriesPage");
        List<Serie> seriesList = ss.getAllSeries();
        if( seriesList == null) {
            throw new UserNotFoundException();
        } else {
            mav.addObject("series", seriesList);
        }
        return mav;
    }

    @RequestMapping("/register")
    public ModelAndView register(
            @RequestParam(value = "email", required = false) final String email,
            @RequestParam("password") final String password
    ) {
        final User user = us.register(email, password);
        return new ModelAndView("redirect:/profile" + user.getId());
    }

    @RequestMapping("/profile/{userId:[0-9]+}")
    public ModelAndView profile(@PathVariable("userId") final long userId) {
        final ModelAndView mav = new ModelAndView("profile");
        mav.addObject("user", us.findById(userId).orElseThrow(UserNotFoundException::new));
        return mav;
    }

    @RequestMapping("/movie/{movieId:[0-9]+}")
    public ModelAndView movieReview(@PathVariable("movieId")final long movieId) {
        final ModelAndView mav = new ModelAndView("infoPage");
        mav.addObject("details", ms.findById(movieId).orElseThrow(UserNotFoundException::new));
        return mav;
    }

    @RequestMapping("/serie/{serieId:[0-9]+}")
    public ModelAndView serieReview(@PathVariable("serieId")final long serieId) {
        final ModelAndView mav = new ModelAndView("infoPage");
        mav.addObject("details", ss.findById(serieId).orElseThrow(UserNotFoundException::new));
        return mav;
    }

    @RequestMapping(value = "/reviewForm/movie/{id:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView reviewFormCreateMovies(@ModelAttribute("registerForm") final ReviewForm reviewForm, @PathVariable("id")final long id) {
        final ModelAndView mav = new ModelAndView("reviewRegistration");
        mav.addObject("id", id);
        return mav;
    }

    @RequestMapping(value = "/reviewForm/movie/{id:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView reviewFormMovie(@Valid @ModelAttribute("registerForm") final ReviewForm form, final BindingResult errors, @PathVariable("id")final long id) {
        if(errors.hasErrors()) {
            return reviewFormCreateMovies(form,id);
        }

        Review newReview = new Review(form.getName(), form.getDescription(), form.getUserName(), form.getEmail(), id);
        newReview.setId(id);
        return new ModelAndView("redirect:/movie/"+id);//Para que redija bien
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView userNotFound(){
        return new ModelAndView("errorPage");
    }

}

