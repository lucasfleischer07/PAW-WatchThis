package ar.edu.itba.paw.webapp.exceptionsMapper;

import ar.edu.itba.paw.webapp.dto.response.ErrorDto;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ReviewNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;

public class PageNotFoundExceptionMapper implements ExceptionMapper<PageNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageNotFoundException.class);

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(PageNotFoundException e) {
        LOGGER.error("PageNotFoundExceptionMapper: PageNotFoundException caught");
        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorDto(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
    }
}
