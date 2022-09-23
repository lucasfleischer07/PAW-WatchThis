package ar.edu.itba.paw.services;

import java.util.*;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;
    private final EmailService emailService;

    private PasswordEncoder passwordEncoder;
    private final Locale locale = LocaleContextHolder.getLocale();

    @Autowired
    public UserServiceImpl(final UserDao userDao, EmailService emailService, final PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Optional<Long> register(User user) {
        try {
            Map<String, Object> mailVariables = new HashMap<>();
            mailVariables.put("to", user.getEmail());
            mailVariables.put("userName", user.getUserName());
            emailService.sendMail("registration", "Welcome to Watch This", mailVariables, locale);
        } catch (MessagingException e) {

        }
        return userDao.create(user.getEmail(), user.getUserName(),passwordEncoder.encode(user.getPassword()), user.getReputation());

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<User> findById(long userId) {
        return userDao.findById(userId);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    @Override
    public void setPassword(String password, User user, String type){
        try {
            Map<String, Object> mailVariables = new HashMap<>();
            mailVariables.put("to", user.getEmail());
            mailVariables.put("userName", user.getUserName());
            if(Objects.equals(type, "forgotten")) {
                String newPassword = generateRandomWord();
                mailVariables.put("newPassword", newPassword);
                emailService.sendMail("passwordForgotten", "Restore Password", mailVariables, locale);
            } else {
                emailService.sendMail("passwordChangeConfirmation", "Restore password confirmation", mailVariables, locale);
                userDao.setPassword(passwordEncoder.encode(password), user);
            }
        } catch (MessagingException e) {

        }
    }

    @Override
    public void setProfilePicture(byte[] profilePicture, User user) {
        userDao.setProfilePicture(profilePicture, user);
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
