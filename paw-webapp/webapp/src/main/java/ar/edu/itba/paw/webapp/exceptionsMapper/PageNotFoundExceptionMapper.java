package ar.edu.itba.paw.webapp.exceptionsMapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PageNotFoundExceptionMapper extends RuntimeException{

}
