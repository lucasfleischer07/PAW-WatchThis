package ar.edu.itba.paw.webapp.validations;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UsernameValidator implements ConstraintValidator<AvailableUserName, String> {

    private final UserService us;

    @Autowired
    public UsernameValidator(final UserService us) {
        this.us = us;
    }

    @Override
    public void initialize(AvailableUserName availableUserName) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> existingUser = us.findByUserName(username);
        if (existingUser.isPresent())
            return false;
        return !username.equals("watchList") && !username.equals("viewedList") && !username.equals("editProfile");
    }
}