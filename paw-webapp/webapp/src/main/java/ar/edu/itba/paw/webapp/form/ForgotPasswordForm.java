package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.AvailableEmail;
import ar.edu.itba.paw.webapp.validations.ExistingEmail;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ForgotPasswordForm {

    @ExistingEmail
    @Size(min = 10, max = 50)
    @Pattern(regexp	= "([+\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
