package ar.edu.itba.paw.webapp.exceptionsMapper;

import ar.edu.itba.paw.webapp.dto.response.ErrorDto;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.VoteNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(value = {MediaType.APPLICATION_JSON})
public class VoteNotFoundExceptionMapper implements ExceptionMapper<VoteNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoteNotFoundExceptionMapper.class);

    @Override
    public Response toResponse(VoteNotFoundException e) {
        LOGGER.error("VoteNotFoundExceptionMapper: VoteNotFoundException caught");
        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorDto(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
    }

}