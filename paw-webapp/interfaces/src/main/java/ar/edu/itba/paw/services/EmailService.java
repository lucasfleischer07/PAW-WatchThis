package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface EmailService {
    void sendRegistrationEmail(User user);
    void sendForgottenPasswordEmail(Optional<User> user);
}
