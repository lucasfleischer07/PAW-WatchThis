package ar.edu.itba.paw.webapp.exceptionsMapper;

import ar.edu.itba.paw.webapp.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.webapp.dto.response.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;

public class ReviewNotFoundExceptionMapper implements ExceptionMapper<ReviewNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewNotFoundException.class);

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ReviewNotFoundException e) {
        LOGGER.error("ReviewNotFoundExceptionMapper: ReviewNotFoundException caught");
        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorDto(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
    }
}
