package ar.edu.itba.paw.webapp.exceptions;

public class VoteNotFoundException extends RuntimeException {
    public VoteNotFoundException() {
        super("Vote not found");
    }
}
