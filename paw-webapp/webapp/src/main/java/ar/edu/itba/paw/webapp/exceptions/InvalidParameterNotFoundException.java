package ar.edu.itba.paw.webapp.exceptions;

import javax.ws.rs.NotFoundException;

public class InvalidParameterNotFoundException extends RuntimeException {
    public InvalidParameterNotFoundException() {
        super("Invalid Parameter.");
    }
}
