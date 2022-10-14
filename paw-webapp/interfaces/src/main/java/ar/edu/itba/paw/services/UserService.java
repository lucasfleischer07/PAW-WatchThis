package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import org.springframework.security.authentication.AuthenticationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> register(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(long userId);
    Optional<User> findByUserName(String userName);
    void setPassword(String password, User user, String type);
    void setProfilePicture(byte[] profilePicture, User user);
    void addToWatchList(User user, Long contentId);
    void deleteFromWatchList(User user, Long contentId);
    List<Content> getWatchList(User user);
    Optional<Long> searchContentInWatchList(User user, Long contentId);
    List<Long> getUserWatchListContent(User user);
    void addToViewedList(User user, Long contentId);
    void deleteFromViewedList(User user, Long contentId);
    List<Content> getUserViewedList(User user);
    Optional<Long> searchContentInViewedList(User user, Long contentId);
    List<Long> getUserViewedListContent(User user);
    void promoteUser(User userId);
    void authWithAuthManager(HttpServletRequest request, String email, String password, AuthenticationManager authenticationManager);
}
