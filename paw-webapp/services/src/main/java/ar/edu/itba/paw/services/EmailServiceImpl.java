package ar.edu.itba.paw.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;
@Transactional
@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine tempEngine;

    @Override
    @Async
    public void sendMail(String template, String subject, Map<String, Object> variables, final Locale locale) throws MessagingException {
        final Context ctx = new Context(locale);
        ctx.setVariables(variables);
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        final String to = (String) variables.get("to");
        message.setTo(to);
        message.setFrom("watchthisassist@gmail.com");
        message.setSubject(subject);
        final String tempContent = tempEngine.process(template, ctx);
        message.setText(tempContent, true);
        mailSender.send(mimeMessage);
    }

}
