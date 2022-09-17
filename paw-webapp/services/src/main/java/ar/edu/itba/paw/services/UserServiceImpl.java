package ar.edu.itba.paw.services;

import java.util.Optional;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(final UserDao userDao,final PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Optional<Long> register(User user) {
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
    public void setPassword(String password, User user){
        userDao.setPassword(passwordEncoder.encode(password), user);
    }

    @Override
    public void setProfilePicture(byte[] profilePicture, User user) {
        userDao.setProfilePicture(profilePicture, user);
    }

}
