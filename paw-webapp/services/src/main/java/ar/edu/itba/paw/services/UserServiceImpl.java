package ar.edu.itba.paw.services;

import java.util.Optional;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(final UserDao userDao){
        this.userDao = userDao;
    }
    @Override
    public Optional<Long> register(User user) {
        return userDao.create(user.getEmail(), user.getUserName());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<User> findById(long userId) {
        return userDao.findById(userId);
    }
}
