package ar.edu.itba.paw.webapp.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.IOException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:mail/mailConfig.properties")
public class MailConfig implements ApplicationContextAware, EnvironmentAware {
    private ApplicationContext appContext;
    private Environment environment;

    @Bean
    public JavaMailSender mailSender() throws IOException {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.environment.getProperty("mail.server.host"));
        mailSender.setPort(Integer.parseInt(this.environment.getProperty("mail.server.port")));
        mailSender.setProtocol(this.environment.getProperty("mail.server.protocol"));
        mailSender.setUsername(this.environment.getProperty("mail.server.username"));
        mailSender.setPassword(this.environment.getProperty("mail.server.password"));
        final Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.host", "smtp.gmail.com");
        javaMailProperties.put("mail.smtp.port", "587");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.starttls.required", "true");
        javaMailProperties.put("mail.smtp.quitwait", "false");
        javaMailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;

    }

    @Bean
    public ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("mail/messages");
        return messageSource;
    }

    @Bean
    public TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        return templateEngine;
    }

    @Override
    public void setApplicationContext(final ApplicationContext appContext) throws BeansException {
        this.appContext = appContext;
    }

    @Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }

    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver tempResolver = new ClassLoaderTemplateResolver();
        tempResolver.setOrder(2);
        tempResolver.setPrefix("/mail/html/");
        tempResolver.setSuffix(".html");
        tempResolver.setTemplateMode(TemplateMode.HTML);
        tempResolver.setCharacterEncoding("UTF-8");
        tempResolver.setCacheable(false);
        return tempResolver;
    }

}
