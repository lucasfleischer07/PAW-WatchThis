package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.AvailableEmail;
import ar.edu.itba.paw.webapp.validations.AvailableUserName;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginForm {

    @AvailableEmail
    @Size(min = 10, max = 50)
    @Pattern(regexp	= "([+\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+")
    private String email;

    @Size(min = 6, max = 50)
    @Pattern(regexp	= "[a-zA-Z0-9!,.:;=+\n\\-_()?<>$%&#@{}\\[\\]|*\"'~/`^\\s]+")
    private String password;

    @Size(min = 6, max = 50)
    @Pattern(regexp	= "[a-zA-Z0-9!,.:;=+\n\\-_()?<>$%&#@{}\\[\\]|*\"'~/`^\\s]+")
    private String confirmPassword;

    @AvailableUserName
    @Size(min = 4, max = 30)
    @Pattern(regexp	= "[a-zA-Z0-9\\s]+")
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
