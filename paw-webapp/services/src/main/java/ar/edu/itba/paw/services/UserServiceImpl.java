package ar.edu.itba.paw.services;

import java.security.Principal;
import java.util.*;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.web.authentication.WebAuthenticationDetails;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;
    private final EmailService emailService;

    private PasswordEncoder passwordEncoder;
    @Autowired
    private MessageSource messageSource;
    private final Locale locale = LocaleContextHolder.getLocale();

    private String generateRandomWord() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(20);
        for(int i = 0; i < 20; i++) {
            char tmp = (char) ('a' + r.nextInt('z' - 'a'));
            sb.append(tmp);
        }
        return sb.toString();
    }

    @Autowired
    public UserServiceImpl(final UserDao userDao, EmailService emailService, final PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Optional<User> register(User user) {
        try {
            Map<String, Object> mailVariables = new HashMap<>();
            mailVariables.put("to", user.getEmail());
            mailVariables.put("userName", user.getUserName());
            emailService.sendMail("registration", messageSource.getMessage("Mail.RegistrationSubject", new Object[]{}, locale), mailVariables, locale);
            return userDao.create(user.getEmail(), user.getUserName(),passwordEncoder.encode(user.getPassword()), user.getReputation());
        } catch (MessagingException e) {}
        return Optional.empty();
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
                userDao.setPassword(passwordEncoder.encode(newPassword), user);
                emailService.sendMail("passwordForgotten", messageSource.getMessage("Mail.PasswordRestoreSubject", new Object[]{}, locale), mailVariables, locale);
            } else {
                userDao.setPassword(passwordEncoder.encode(password), user);
                emailService.sendMail("passwordChangeConfirmation", messageSource.getMessage("Mail.PasswordChangeSubject", new Object[]{}, locale), mailVariables, locale);
            }
        } catch (MessagingException e) {}
    }

    @Override
    public void setProfilePicture(byte[] profilePicture, User user) {
        userDao.setProfilePicture(profilePicture, user);
    }

    @Override
    public void addToWatchList(User user, Long contentId) {
        userDao.addToWatchList(user, contentId);
    }


    @Override
    public void deleteFromWatchList(User user, Long contentId) {
        userDao.deleteFromWatchList(user, contentId);
    }

    @Override
    public List<Content> getWatchList(User user) {
        return userDao.getWatchList(user);
    }

    @Override
    public Optional<Long> searchContentInWatchList(User user, Long contentId) {
        return userDao.searchContentInWatchList(user, contentId);
    }

    @Override
    public List<Long> getUserWatchListContent(User user) {
        return userDao.getUserWatchListContent(user);
    }

    @Override
    public void addToViewedList(User user, Long contentId) {
        userDao.addToViewedList(user, contentId);
    }

    @Override
    public void deleteFromViewedList(User user, Long contentId) {
        userDao.deleteFromViewedList(user, contentId);
    }

    @Override
    public List<Content> getUserViewedList(User user) {
        return userDao.getUserViewedList(user);
    }

    @Override
    public Optional<Long> searchContentInViewedList(User user, Long contentId) {
        return userDao.searchContentInViewedList(user, contentId);
    }

    @Override
    public List<Long> getUserViewedListContent(User user) {
        return userDao.getUserViewedListContent(user);
    }

    @Override
    public void promoteUser(User user) {
        try {
            Map<String, Object> mailVariables = new HashMap<>();
            mailVariables.put("to", user.getEmail());
            mailVariables.put("userName", user.getUserName());
            userDao.promoteUser(user);
            emailService.sendMail("adminConfirmation", messageSource.getMessage("Mail.AdminConfirmation", new Object[]{}, locale), mailVariables, locale);
        } catch (MessagingException ignored) {}
    }

    @Override
    public void authWithAuthManager(HttpServletRequest request, String email, String password, AuthenticationManager authenticationManager){
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        request.getSession();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password, authorities);
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
