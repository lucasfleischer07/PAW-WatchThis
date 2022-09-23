package ar.edu.itba.paw.services;


import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private TemplateEngine htmlTemplateEngine;

    private static final String WATCHTHIS_EMAIL = "watchthisassist@gmail.com";

//    private final UserService us;
//    @Autowired
//    public EmailServiceImpl(UserService us) {
//        this.us = us;
//    }


    @Override
    public void sendRegistrationEmail(User user) {

    }

    @Async
    @Override
    public void sendMail(String template, String subject, Map<String, Object> variables, final Locale locale) throws MessagingException {
        final Context ctx = new Context(locale);
        ctx.setVariables(variables);

        final MimeMessage mimeMessage = emailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        final String to = (String) variables.get("to");
        message.setSubject(subject);
        message.setFrom(WATCHTHIS_EMAIL);
        message.setTo(to);

        final String htmlContent = htmlTemplateEngine.process(template, ctx);
        message.setText(htmlContent, true);

        emailSender.send(mimeMessage);
    }

    @Override
    public void sendForgottenPasswordEmail(User user) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(user.getEmail());
//        message.setSubject("Reset password");
//        String newPassword = generateRandomWord();
//        message.setText("Hello " + user.getUserName() + "!\n\nHere is your new password: " + newPassword + ".\n\nRegards.\n\nWatch This support.");
//        us.setPassword(newPassword, user);
//        emailSender.send(message);
    }

    @Override
    public void sendRestorePasswordEmail(User user) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(user.getEmail());
//        message.setSubject("Password restore successfully!");
//        String newPassword = generateRandomWord();
//        message.setText("Hello " + user.getUserName() + "!\n\nYour password has been restore correctly.\n\nRegards.\n\nWatch This support.");
//        us.setPassword(newPassword, user);
//        emailSender.send(message);
    }

}
