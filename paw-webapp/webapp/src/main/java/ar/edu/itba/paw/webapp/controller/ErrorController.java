package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


    @Controller
    public class ErrorController {

        @RequestMapping(value = "errorPage", method = RequestMethod.GET)
        public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

            ModelAndView errorPage = new ModelAndView("errorPage");
            String errorTitle = "";
            String errorBody ="";
            int httpErrorCode = getErrorCode(httpRequest);

            switch (httpErrorCode) {
                case 400: {
                    errorTitle = "Bad Request";
                    errorBody ="Request could not be processed due to bad syntax.";
                    break;
                }
                case 401: {
                    errorTitle = "Unauthorized";
                    errorBody ="This page is not publicly available.";

                    break;
                }
                case 404: {
                    errorTitle = "Page not found";
                    errorBody ="Sorry we can't find your page. Let's try to get you back on track.";

                    break;
                }
                case 500: {
                    errorTitle = "Internal Server Error";
                    errorBody ="There was a server error while processing your request.";

                    break;
                }
            }
            errorPage.addObject("errorCode", httpErrorCode);
            errorPage.addObject("errorTitle", errorTitle);
            errorPage.addObject("errorBody", errorBody);
            return errorPage;
        }

        private int getErrorCode(HttpServletRequest httpRequest) {
            return (Integer) httpRequest
                    .getAttribute("javax.servlet.error.status_code");
        }
    }

