package ar.edu.itba.paw.webapp.exceptions;

import javax.ws.rs.NotFoundException;

public class ContentNotFoundException extends NotFoundException {
    public ContentNotFoundException() {
        super("Content not found");
    }
}
