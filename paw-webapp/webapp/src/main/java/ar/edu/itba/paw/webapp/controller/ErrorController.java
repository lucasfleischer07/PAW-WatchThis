package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


    @Controller
    public class ErrorController {

        @RequestMapping(value = "/error", method = RequestMethod.GET)
        public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
            ModelAndView errorPage = new ModelAndView("errorPage");
            errorPage.addObject("errorCode",getErrorCode(httpRequest));
            return errorPage;
        }

        private int getErrorCode(HttpServletRequest httpRequest) {
            return (Integer) httpRequest
                    .getAttribute("javax.servlet.error.status_code");
        }

    }

