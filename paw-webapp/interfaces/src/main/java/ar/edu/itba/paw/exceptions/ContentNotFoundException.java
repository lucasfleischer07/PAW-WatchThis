package ar.edu.itba.paw.exceptions;

public class ContentNotFoundException extends RuntimeException{
    public ContentNotFoundException() {
        super("Error.Title404");
    }
}
