package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Movie;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Serie;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.MovieService;
import ar.edu.itba.paw.services.ReviewService;
import ar.edu.itba.paw.services.SerieService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class HelloWorldController {

    private final UserService us;
    private final MovieService ms;
    private final SerieService ss;
    private final ReviewService rs;

    @Autowired  //Para indicarle que este es el constructor que quiero que use
    public HelloWorldController(final UserService us, final MovieService ms, SerieService ss, ReviewService rs){
        this.us = us;
        this.ms = ms;
        this.ss = ss;
        this.rs = rs;
    }


    // * ----------------------------------- Movie Info -----------------------------------------------------------------------
    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("index");
        List<Movie> movieList = ms.getAllMovies();

        if(movieList == null) {
            throw new PageNotFoundException();
        } else {
            mav.addObject("movies", movieList);
        }
        return mav;
    }

    @RequestMapping("/movie/{movieId:[0-9]+}")
    public ModelAndView movieReview(@PathVariable("movieId")final long movieId) {
        final ModelAndView mav = new ModelAndView("infoPage");
        mav.addObject("details", ms.findById(movieId).orElseThrow(PageNotFoundException::new));
        List<Review> reviewList = rs.getAllReviews("movie",movieId);
        if( reviewList == null) {
            throw new PageNotFoundException();
        } else {
            mav.addObject("reviews", reviewList);
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
    // * -----------------------------------------------------------------------------------------------------------------------

    // *  ----------------------------------- Movies and Serie Filters -----------------------------------------------------------------------

    @RequestMapping("/{type:movies|series}/filters")
    public ModelAndView moviesWithFilters(
            @PathVariable("type") final String type,
            @RequestParam(name = "durationFrom",defaultValue = "ANY")final String durationFrom,
            @RequestParam(name = "durationTo",defaultValue = "ANY")final String durationTo,
            @RequestParam(name = "genre", defaultValue = "ANY")final String genre) {
        ModelAndView mav = null;
        if(Objects.equals(type, "movies")) {
            mav = new ModelAndView("index");
            mav.addObject("genre",genre);
            mav.addObject("durationFrom",durationFrom);
            mav.addObject("durationTo",durationTo);
            List<Movie> movieListFilter;
            if(!Objects.equals(genre, "ANY") && Objects.equals(durationFrom, "ANY")) {
                movieListFilter = ms.findByGenre(genre);
            } else if(Objects.equals(genre, "ANY") && !Objects.equals(durationFrom, "ANY")) {
                movieListFilter = ms.findByDuration(Integer.parseInt(durationFrom), Integer.parseInt(durationTo));
            } else if(!Objects.equals(durationFrom, "ANY") && !Objects.equals(genre, "ANY")){    // Caso de que si los filtros estan vacios
                movieListFilter = ms.findByDurationAndGenre(genre,Integer.parseInt(durationFrom),Integer.parseInt(durationTo));
            } else{
                movieListFilter = ms.getAllMovies();
            }

            if(movieListFilter == null) {
                throw new PageNotFoundException();
            } else {
                mav.addObject("movies", movieListFilter);
            }
            return mav;
        } else if(Objects.equals(type, "series")) {
            mav = new ModelAndView("seriesPage");
            mav.addObject("genre",genre);
            mav.addObject("durationFrom",durationFrom);
            mav.addObject("durationTo",durationTo);
            List<Serie> serieListFilter;
            if(!Objects.equals(genre, "ANY") && (Objects.equals(durationFrom, "ANY"))) {
                serieListFilter = ss.findByGenre(genre);
            } else if(Objects.equals(genre, "ANY") && (!Objects.equals(durationFrom, "ANY"))) {
                serieListFilter = ss.findByDuration(Integer.parseInt(durationFrom), Integer.parseInt(durationTo));
            } else if(!Objects.equals(durationFrom, "ANY") && !Objects.equals(genre, "ANY")){    // Caso de que si los filtros estan vacios
                serieListFilter = ss.findByDurationAndGenre(genre,Integer.parseInt(durationFrom),Integer.parseInt(durationTo));
            }
            else {    // Caso de que si los filtros estan vacios
                serieListFilter = ss.getAllSeries();
            }

            if(serieListFilter == null) {
                throw new PageNotFoundException();
            } else {
                mav.addObject("series", serieListFilter);
            }
            return mav;
        }
        return mav;
    }

    // * -----------------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Serie Info -----------------------------------------------------------------------
    @RequestMapping("/series")
    public ModelAndView series() {
        final ModelAndView mav = new ModelAndView("seriesPage");
        List<Serie> seriesList = ss.getAllSeries();
        if( seriesList == null) {
            throw new PageNotFoundException();
        } else {
            mav.addObject("series", seriesList);
        }
        return mav;
    }

//    @RequestMapping("/series/filters")
//    public ModelAndView seriesWithFilters(
//            @RequestParam(name = "durationFrom",defaultValue = "ANY")final String durationFrom,
//            @RequestParam(name = "durationTo",defaultValue = "ANY")final String durationTo,
//            @RequestParam(name = "genre", defaultValue = "ANY")final String genre) {
//        final ModelAndView mav = new ModelAndView("index");
//        List<Serie> serieListFilter;
//
//        if(!Objects.equals(genre, "ANY") && Objects.equals(durationFrom, "ANY")) {
//            serieListFilter = ss.findByGenre(genre);
//        } else if(Objects.equals(genre, "ANY") && !Objects.equals(durationFrom, "ANY")) {
//            serieListFilter = ss.findByDuration(Integer.parseInt(durationFrom), Integer.parseInt(durationTo));
//        } else {    // Caso de que si los filtros estan vacios
//            serieListFilter = ss.getAllSeries();
//        }
//
//        if(serieListFilter == null) {
//            throw new UserNotFoundException();
//        } else {
//            mav.addObject("series", serieListFilter);
//        }
//        return mav;
//    }

    @RequestMapping("/serie/{serieId:[0-9]+}")
    public ModelAndView serieReview(@PathVariable("serieId")final long serieId) {
        final ModelAndView mav = new ModelAndView("infoPage");
        mav.addObject("details", ss.findById(serieId).orElseThrow(PageNotFoundException::new));
        List<Review> reviewList = rs.getAllReviews("serie",serieId);
        if( reviewList == null) {
            throw new PageNotFoundException();
        } else {
            mav.addObject("reviews", reviewList);
        }
        return mav;
    }
    // * -----------------------------------------------------------------------------------------------------------------------



    // * ----------------------------------- Movies Review -----------------------------------------------------------------------
    @RequestMapping(value = "/reviewForm/movie/{id:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView reviewFormCreateMovies(@ModelAttribute("registerForm") final ReviewForm reviewForm, @PathVariable("id")final long id) {
        final ModelAndView mav = new ModelAndView("reviewRegistration");
        mav.addObject("id", id);
        mav.addObject("type", "movie");
        return mav;
    }

    @RequestMapping(value = "/reviewForm/movie/{id:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView reviewFormMovie(@Valid @ModelAttribute("registerForm") final ReviewForm form, final BindingResult errors, @PathVariable("id")final long id) {
        if(errors.hasErrors()) {
            return reviewFormCreateMovies(form,id);
        }
        User newUser = new User(null,form.getEmail(),form.getUserName());
        //Primero intenta agregar el usuario, luego intenta agregar la review
        Optional<Long> userId = us.register(newUser);
        //Falta Agregar mensaje de error para el caso -1 (si falla en el pedido)
        if(userId.get() != -1) {
            Review newReview = new Review( null,"movie",id,null,userId.get(),form.getName(),form.getDescription());    //ReviewId va en null por que eso lo asigna la tabla
            newReview.setId(id);
            rs.addReview(newReview);
        }
        return new ModelAndView("redirect:/movie/"+id);
    }
    // * -----------------------------------------------------------------------------------------------------------------------

    
    // * ----------------------------------- Serie Review -----------------------------------------------------------------------
    @RequestMapping(value = "/reviewForm/serie/{id:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView reviewFormCreateSeries(@ModelAttribute("registerForm") final ReviewForm reviewForm, @PathVariable("id")final long id) {
        final ModelAndView mav = new ModelAndView("reviewRegistration");
        mav.addObject("id", id);
        mav.addObject("type", "serie");
        return mav;
    }

    @RequestMapping(value = "/reviewForm/serie/{id:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView reviewFormSeries(@Valid @ModelAttribute("registerForm") final ReviewForm form, final BindingResult errors, @PathVariable("id")final long id) {
        if(errors.hasErrors()) {
            return reviewFormCreateSeries(form,id);
        }
        User newUser = new User(null,form.getEmail(),form.getUserName());
        //Primero intenta agregar el usuario, luego intenta agregar la review
        Optional<Long> userId = us.register(newUser);
        //Falta Agregar mensaje de error para el caso -1 (si falla en el pedido)
        if (userId.get() != -1) {
            Review newReview = new Review(null,"serie",null,id,userId.get(),form.getName(),form.getDescription() );    //ReviewId va en null por que eso lo asigna la tabla
            newReview.setId(id);
            rs.addReview(newReview);

        }
        return new ModelAndView("redirect:/serie/"+id);
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

