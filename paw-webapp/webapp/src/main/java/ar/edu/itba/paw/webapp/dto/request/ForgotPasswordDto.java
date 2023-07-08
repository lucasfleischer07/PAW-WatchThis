package ar.edu.itba.paw.webapp.dto.request;

import ar.edu.itba.paw.webapp.dto.validations.ExistingEmail;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ForgotPasswordDto {
    @ExistingEmail
    @Size(min = 10, max = 50, message = "...")
    @Pattern(regexp	= "([+\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+", message = "...")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
