package ar.edu.itba.paw.webapp.dto.validations;

import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class OldPasswordValidator implements ConstraintValidator<CorrectOldPassword, String> {


    private final UserService us;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public OldPasswordValidator(final UserService us, PasswordEncoder passwordEncoder) {
        this.us = us;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void initialize(CorrectOldPassword correctOldPassword) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Optional<User> loggedUser = us.findByEmail(auth.getName());
        return true;//loggedUser.map(user -> user.getPassword().equals(passwordEncoder.encode(s))).orElse(false);

    }


}
