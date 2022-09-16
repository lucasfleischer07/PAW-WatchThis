package ar.edu.itba.paw.services;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
