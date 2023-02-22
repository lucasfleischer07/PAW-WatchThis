package ar.edu.itba.paw.webapp.exceptionsMapper;

import ar.edu.itba.paw.webapp.dto.response.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BadRequestExceptionMapper.class);

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(BadRequestException e) {
        LOGGER.error("BadRequestExceptionMapper: BadRequestException caught");
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDto(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
    }
}
