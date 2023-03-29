package ar.edu.itba.paw.webapp.exceptions;

import javax.ws.rs.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User not found");
    }
}
