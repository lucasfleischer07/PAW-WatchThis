package ar.edu.itba.paw.webapp.validations;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class EmailExistingValidator implements ConstraintValidator<ExistingEmail, String> {

    private final UserService us;
    @Autowired
    public EmailExistingValidator(final UserService us){
        this.us = us;
    }

    @Override
    public void initialize(ExistingEmail availableEmail) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> existingEmail = us.findByEmail(email);
        if(existingEmail.isPresent())
            return true;
        return false;
    }
}
