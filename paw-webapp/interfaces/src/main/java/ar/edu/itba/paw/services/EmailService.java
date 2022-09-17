package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;

public interface EmailService {
    void sendRegistrationEmail(User user);
    void sendForgottenPasswordEmail(String email);
}
