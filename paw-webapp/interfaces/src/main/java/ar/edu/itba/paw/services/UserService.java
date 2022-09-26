package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<Long> register(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(long userId);
    Optional<User> findByUserName(String userName);
    void setPassword(String password, User user, String type);
    void setProfilePicture(byte[] profilePicture, User user);
    void addToWatchList(User user, Long contentId);
    void deleteFromWatchList(User user, Long contentId);
    List<Content> getWatchList(User user);
    Optional<Long> searchContentInWatchList(User user, Long contentId);
    void promoteUser(Long userId);
    List<Long> getUserWatchListContent(User user);
}
