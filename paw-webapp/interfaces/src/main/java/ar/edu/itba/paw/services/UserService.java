package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;
import java.util.Optional;

public interface UserService {
    //Intenta agregar el usuario si no existe y devuelve su id luego de agregarlo
    Optional<Long> register(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(long userId);

    void setPassword(String password,String email);
}
