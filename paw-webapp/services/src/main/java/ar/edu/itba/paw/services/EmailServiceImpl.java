package ar.edu.itba.paw.services;


import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    private final UserService us;
    @Autowired
    public EmailServiceImpl(UserService us) {
        this.us = us;
    }


    //    TODO: VER QUE METER ACA
    public void sendRegistrationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Welcome to the Watch This family!");
        message.setText("Welcome " + user.getUserName() + "!\n\nWe are proud of having you as part of our family.\nRegards.\nWatch This support.");
        emailSender.send(message);
    }

    @Override
    public void sendForgottenPasswordEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Reset password");
        String newPassword = generateRandomWord();
        message.setText("Hello " + user.getUserName() + "!\n\nHere is your new password: " + newPassword + ".\n\nRegards.\n\nWatch This support.");
        us.setPassword(newPassword, user);
        emailSender.send(message);
    }

    @Override
    public void sendRestorePasswordEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Password restore successfully!");
        String newPassword = generateRandomWord();
        message.setText("Hello " + user.getUserName() + "!\n\nYour password has been restore correctly.\n\nRegards.\n\nWatch This support.");
        us.setPassword(newPassword, user);
        emailSender.send(message);
    }

    private String generateRandomWord() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(20);
        for(int i = 0; i < 20; i++) {
            char tmp = (char) ('a' + r.nextInt('z' - 'a'));
            sb.append(tmp);
        }
        return sb.toString();
    }
}
