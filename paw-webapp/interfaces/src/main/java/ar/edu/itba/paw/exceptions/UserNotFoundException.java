package ar.edu.itba.paw.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("Error.Title404");
    }
}
