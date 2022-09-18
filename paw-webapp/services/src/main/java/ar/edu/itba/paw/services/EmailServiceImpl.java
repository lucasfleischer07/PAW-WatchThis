package ar.edu.itba.paw.services;


import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    //    TODO: VER QUE METER ACA
    public void sendRegistrationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Welcome to the Watch This family!");
        message.setText("Welcome " + user.getUserName() + " We are proud of having you as part of our family.\nRegards.\nWatch This support.");
        emailSender.send(message);
    }

//    TODO: VER QUE METER ACA
    @Override
    public void sendForgottenPasswordEmail(Optional<User> user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.get().getEmail());
        message.setSubject("Reset password");
        message.setText("Hello " + user.get().getUserName() + "here is your new password: " + user.get().getPassword() + ".\nRegards.\nWatch This support.");
        emailSender.send(message);
    }
}
