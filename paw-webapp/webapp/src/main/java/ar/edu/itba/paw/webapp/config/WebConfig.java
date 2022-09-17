package ar.edu.itba.paw.webapp.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@EnableWebMvc
@ComponentScan({
        "ar.edu.itba.paw.webapp.controller",
        "ar.edu.itba.paw.services",
        "ar.edu.itba.paw.persistance"
})
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }


    @Bean
    public DataSource dataSource(){
//        * Conexion a Heroku
        String dbUrl="jdbc:postgresql://ec2-44-209-158-64.compute-1.amazonaws.com:5432/dejahu751a4sa5";
        String username = "nnsbcsmyzbkewt";
        String password = "2d3518851436a2f7f6c4367b2c79aa6f66f456a436aeaf02f44cd07a80497f27";
        final SimpleDriverDataSource basicDataSource = new SimpleDriverDataSource();
        basicDataSource.setDriverClass(org.postgresql.Driver.class);
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        return basicDataSource;

//        * Conexion a la db de  la catedra
//        String dbUrl="jdbc:postgresql://10.16.1.110:5432/paw-2022b-3";
//        String username = "paw-2022b-3";
//        String password = "h79KBcheb";
//        final SimpleDriverDataSource basicDataSource = new SimpleDriverDataSource();
//        basicDataSource.setDriverClass(org.postgresql.Driver.class);
//        basicDataSource.setUrl(dbUrl);
//        basicDataSource.setUsername(username);
//        basicDataSource.setPassword(password);
//        return basicDataSource;


    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    // * ----------------------------------- Emails -----------------------------------------------------------------------
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("watchthisassist@gmail.com");
        mailSender.setPassword("scyvyihczrzgdifw");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    // * -----------------------------------------------------------------------------------------------------------------------

}
