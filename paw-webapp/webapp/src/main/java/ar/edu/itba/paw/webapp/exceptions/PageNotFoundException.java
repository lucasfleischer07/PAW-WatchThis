package ar.edu.itba.paw.webapp.exceptions;

import javax.ws.rs.NotFoundException;

public class PageNotFoundException extends NotFoundException {
    public PageNotFoundException(){
         super("Page not found");
    }
}
