package ar.edu.itba.paw.webapp.exceptionsMapper;

import ar.edu.itba.paw.webapp.dto.response.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForbiddenExceptionMapper.class);

    @Override
    public Response toResponse(ForbiddenException e) {
        LOGGER.error("ForbiddenExceptionMapper: ForbiddenException caught");
        return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDto(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
    }

}
