package ar.edu.itba.paw.webapp.dto.validations;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class EmailValidator implements ConstraintValidator<AvailableEmail, String> {
    private final UserService us;
    @Autowired
    public EmailValidator(final UserService us){
        this.us = us;
    }

    @Override
    public void initialize(AvailableEmail availableEmail) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> existingEmail = us.findByEmail(email);
        if(existingEmail.isPresent())
            return false;
        return true;
    }

}
