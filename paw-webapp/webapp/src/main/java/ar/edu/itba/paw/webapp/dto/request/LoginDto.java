package ar.edu.itba.paw.webapp.dto.request;

import ar.edu.itba.paw.webapp.validations.AvailableEmail;
import ar.edu.itba.paw.webapp.validations.AvailableUserName;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginDto {
//    TODO: METER LOS MENSAJES DE ERROR EN CASO DE QUE ESTE VACIO

    @AvailableEmail
    @Size(min = 10, max = 50, message = "...")
    @Pattern(regexp	= "((([+\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3}))+)?", message = "...")
    private String email;

    @Size(min = 6, max = 50, message = "...")
    @Pattern(regexp	= "([a-zA-Z0-9ñáéíóú!,.:;=+\n\\-_()?<>$%&#@{}\\[\\]|*\"'~/`^\\s]+)?", message = "...")
    private String password;

    @Size(min = 6, max = 50, message = "...")
    @Pattern(regexp	= "([a-zA-Z0-9ñáéíóú!,.:;=+\n\\-_()?<>$%&#@{}\\[\\]|*\"'~/`^\\s]+)?", message = "...")
    private String confirmPassword;

    @AvailableUserName
    @Size(min = 4, max = 30, message = "...")
    @Pattern(regexp	= "([a-zA-Z0-9ñ\\s]+)?", message = "...")
    private String username;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
