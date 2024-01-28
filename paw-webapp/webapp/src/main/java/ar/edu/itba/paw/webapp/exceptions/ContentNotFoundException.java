package ar.edu.itba.paw.webapp.exceptions;

import javax.ws.rs.NotFoundException;

public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException() {
        super("Content not found");
    }
}
