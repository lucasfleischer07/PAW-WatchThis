package ar.edu.itba.paw.webapp.exceptionsMapper;

import ar.edu.itba.paw.webapp.dto.response.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServerErrorExceptionMapper implements ExceptionMapper<ServerErrorException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BadRequestExceptionMapper.class);

    @Context
    private UriInfo uriInfo;
    @Override
    public Response toResponse(ServerErrorException e) {
        LOGGER.error("ServerErrorExceptionMapper: ServerErrorException caught");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorDto(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
    }
}
