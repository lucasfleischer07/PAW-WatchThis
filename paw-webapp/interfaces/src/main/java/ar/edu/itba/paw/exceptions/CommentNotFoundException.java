package ar.edu.itba.paw.exceptions;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException() {
        super("Error.Title404");
    }
}
