package ar.edu.itba.paw.webapp.exceptions;
import javax.ws.rs.NotFoundException;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() {
        super("Comment not found");
    }
}
