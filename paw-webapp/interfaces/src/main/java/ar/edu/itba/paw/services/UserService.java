package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.PageWrapper;
import ar.edu.itba.paw.models.User;
import org.springframework.security.authentication.AuthenticationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> register(String userEmail, String userName, String password);
    Optional<User> findByEmail(String email);
    Optional<User> findById(long userId);
    Optional<User> findByUserName(String userName);
    void setPassword(String password, User user);
    void setProfilePicture(byte[] profilePicture, User user);
    void addToWatchList(User user, Content toAdd);
    void deleteFromWatchList(User user, Content toDelete);
    PageWrapper<Content> getWatchList(User user, int page, int pageSize);
    Optional<Long> searchContentInWatchList(User user, Long contentId);
    void addToViewedList(User user, Content toAdd);
    void deleteFromViewedList(User user, Content toDelete);
    PageWrapper<Content> getUserViewedList(User user,int page,int pageSize);
    Optional<Long> searchContentInViewedList(User user, Long contentId);
    void promoteUser(User userId);
    List<Long>getUserWatchListContent(User user);
    List<Long>getUserViewedListContent(User user);
    boolean checkPassword( String uncheckedPassword, User user);
    void authWithAuthManager(HttpServletRequest request, String email, String password, AuthenticationManager authenticationManager);
    String getUserImageHash(User user);
}
