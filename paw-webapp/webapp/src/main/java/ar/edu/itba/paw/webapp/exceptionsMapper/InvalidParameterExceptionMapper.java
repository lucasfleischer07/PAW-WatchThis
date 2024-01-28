package ar.edu.itba.paw.webapp.exceptionsMapper;

import ar.edu.itba.paw.webapp.dto.response.ErrorDto;
import ar.edu.itba.paw.webapp.exceptions.InvalidParameterNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(value = {MediaType.APPLICATION_JSON})
public class InvalidParameterExceptionMapper implements ExceptionMapper<InvalidParameterNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidParameterExceptionMapper.class);

    @Override
    public Response toResponse(InvalidParameterNotFoundException e) {
        LOGGER.error("InvalidParameterExceptionMapper: InvalidParameterException caught");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorDto(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
    }
}
