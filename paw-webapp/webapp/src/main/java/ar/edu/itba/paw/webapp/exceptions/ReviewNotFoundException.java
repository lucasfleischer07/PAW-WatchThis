package ar.edu.itba.paw.webapp.exceptions;

import javax.ws.rs.NotFoundException;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException() {
        super("Review not found");
    }
}
