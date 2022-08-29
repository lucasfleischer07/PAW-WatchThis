package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;
import java.util.Optional;

public interface UserService {

    User register(String email, String password);

    Optional<User> findByEmail(String email);

    Optional<User> findById(long userId);
}
