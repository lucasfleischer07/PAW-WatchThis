package ar.edu.itba.paw.webapp.exceptions;
import javax.ws.rs.NotFoundException;

public class CommentNotFoundException extends NotFoundException {
    public CommentNotFoundException() {
        super("Comment not found");
    }
}
