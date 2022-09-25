package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<Long> create(String userEmail, String userName, String password, Long rating);
    Optional<User> findByEmail(String email);
    Optional<User> findById(long userId);
    Optional<User> findByUserName(String userName);
    void setPassword(String password, User user);
    void setProfilePicture(byte[] profilePicture, User user);
    void addToWatchList(User user, Long contentId);
    List<Content> getWatchList(User user);
}

