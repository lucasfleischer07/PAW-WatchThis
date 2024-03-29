package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;
import javax.mail.MessagingException;

import java.util.Locale;
import java.util.Map;

public interface EmailService {
    void sendMail(String template, String subject, Map<String, Object> variables, final Locale locale) throws MessagingException;
}
