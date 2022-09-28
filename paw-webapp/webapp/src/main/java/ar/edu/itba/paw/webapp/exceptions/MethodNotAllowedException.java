package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class MethodNotAllowedException extends RuntimeException{

}
