package ar.edu.itba.paw.webapp.dto.response;

import javax.ws.rs.core.UriInfo;
import ar.edu.itba.paw.models.User;

public class UserDto {
    private String username;
    private String email;

    public UserDto(UriInfo uriInfo, User user) {
//        TODO: Falta lo de la uri

        this.email = user.getEmail();
        this.username = user.getUserName();
    }
}
