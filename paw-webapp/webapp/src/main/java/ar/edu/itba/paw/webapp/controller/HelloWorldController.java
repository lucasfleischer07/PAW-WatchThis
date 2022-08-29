package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.MovieService;
import ar.edu.itba.paw.services.SerieService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    private final UserService us;
    private final MovieService ms;
    private final SerieService ss;


    //----------------------------------------------
    //Inyeccion de dependencias por el contructor
    //----------------------------------------------

    // @Qualifier("userServiceImpl") es para indicarle cual implementacion quiero que use y evitar
    // conflictos en la inyeccion de dependencias

    //    public HelloWorldController(@Qualifier("userServiceImpl") final UserService us){
    //        this.us = us;
    //    }

    // La otra forma es agregarle @Primary a la clase que quiero que use,
    // sin embargo el @Qualifier tiene mayor prioridad

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
//        * Aca deberia haber un get o algo, pero el pibe deja el register y eso tiraba error
//        final User user = us.register("paw@itba.edu.ar", "secret");
        mav.addObject("username", "PAW");
//        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping("/register")
    public ModelAndView register(
            @RequestParam(value = "email", required = false) final String email,
            @RequestParam("password") final String password
    ) {
        final User user = us.register(email, password);
        return new ModelAndView("redirect:/profile" + user.getId());
        // Con esto puedo ingresar a:
        // http://localhost:8080/register?email=foo@bar.com&password=secret
        // (aunque el mail no es requerido pq le pusimos un valor default)
        // y esto me redirige a http://localhost:8080/profile/1
    }

    //Puedo agregar una validacion como expresion regular para userId
    @RequestMapping("/profile/{userId:[0-9]+}")
    public ModelAndView profile(@PathVariable("userId") final long userId) {
        final ModelAndView mav = new ModelAndView("profile");
        mav.addObject("user", us.findById(userId).orElseThrow(UserNotFoundException::new));
        return mav;
        // Con esto puedo ingresar a:
        // http://localhost:8080/profile/1
    }

    @RequestMapping("/movies")
    public ModelAndView movies() {
        final ModelAndView mav = new ModelAndView("movies");
//        final Movie movie = ms.findByName();
//        TODO: Esta hardcodeado el nombre de la pelicula
        mav.addObject("movies", ms.findByName("Rush").orElseThrow(UserNotFoundException::new));

        return mav;
    }

    @RequestMapping("/series")
    public ModelAndView series() {
        final ModelAndView mav = new ModelAndView("series");
//        final Movie movie = ms.findByName();
//        TODO: Esta hardcodeado el nombre de la pelicula
        mav.addObject("serie", ss.findByName("Sherlock").orElseThrow(UserNotFoundException::new));
        return mav;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView userNotFound(){
        return new ModelAndView("404");
    }

}

