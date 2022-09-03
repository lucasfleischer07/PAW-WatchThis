//package ar.edu.itba.paw.webapp.controller;
//
//import ar.edu.itba.paw.models.Review;
//import ar.edu.itba.paw.webapp.form.ReviewForm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.validation.Valid;
//
//@Controller
//public class ReviewController {
//
//    @Autowired
//    public ReviewController() {
//    }
//
//    @RequestMapping(value = "/reviewForm", method = {RequestMethod.GET})
//    public ModelAndView reviewFormCreate(@ModelAttribute("registerForm") final ReviewForm reviewForm) {
//        return new ModelAndView("reviewRegistration");
//    }
//
//    @RequestMapping(value = "/reviewForm", method = {RequestMethod.POST})
//    public ModelAndView reviewForm(@Valid @ModelAttribute("registerForm") final ReviewForm form, final BindingResult errors) {
//        if(errors.hasErrors()) {
//            return reviewFormCreate(form);
//        }
//
//        Review newReview = new Review(form.getName(), form.getDescription(), form.getUserName(), form.getEmail(),form.getMovieId());
//        //Esto tiene que rederigir a la movie en la que estaba con una confirmacion pero no se como conseguir el Id
//        return new ModelAndView("redirect:/movie/"+newReview.getMovieId());//Para que redija bien
//    }
//}
