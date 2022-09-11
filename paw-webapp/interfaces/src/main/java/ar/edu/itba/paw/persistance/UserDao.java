package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserDao {

    Optional<Long> create(String userEmail, String password);

    Optional<User> findByEmail(String email);

    Optional<User> findById(long userId);

    void setPassword(String password,String email);


}
