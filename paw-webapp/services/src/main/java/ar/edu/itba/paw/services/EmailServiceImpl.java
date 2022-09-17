package ar.edu.itba.paw.services;


import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    //    TODO: VER QUE METER ACA
    public void sendRegistrationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Welcome to the Watch This family!");
        message.setText("We are proud of having you as part of our family");
        emailSender.send(message);
    }

//    TODO: VER QUE METER ACA
    @Override
    public void sendForgottenPasswordEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset password");
        message.setText("We are generating a new password so you can access.");
        emailSender.send(message);
    }
}
