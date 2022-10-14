package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class UserJpaDao implements UserDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> create(String userEmail, String userName, String password, Long rating) {
        final User user = new User(userName,userEmail,password);
        em.persist(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        final TypedQuery<User> query=em.createQuery( "FROM User WHERE email = :email",User.class);
        query.setParameter("email",email);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> findById(long userId) {
        return Optional.ofNullable(em.find(User.class,userId));

    }

    @Override
    public Optional<User> findByUserName(String userName) {

        final TypedQuery<User> query=em.createQuery( "FROM User WHERE userName = :userName",User.class);
        query.setParameter("userName",userName);
        query.
        return query.getResultList().stream().findFirst();
    }

    @Override
    public void setPassword(String password, User user) {
        Optional<User> maybeUser=findByEmail(user.getEmail());
        if(!maybeUser.isPresent()){
            return;}
        User maybe=maybeUser.get();
        maybe.setPassword(password);
        em.persist(user);

    }

    @Override
    public void setProfilePicture(byte[] profilePicture, User user) {
        Optional<User> maybeUser=findByEmail(user.getEmail());
        if(!maybeUser.isPresent()){
            return;}
        User maybe=maybeUser.get();
        maybe.setImage(profilePicture);
        em.persist(user);
    }

    @Override
    public void addToWatchList(User user, Long contentId) {

    }

    @Override
    public void deleteFromWatchList(User user, Long contentId) {

    }

    @Override
    public List<Content> getWatchList(User user) {
        return null;
    }

    @Override
    public Optional<Long> searchContentInWatchList(User user, Long contentId) {
        return Optional.empty();
    }

    @Override
    public List<Long> getUserWatchListContent(User user) {
        return null;
    }

    @Override
    public void addToViewedList(User user, Long contentId) {

    }

    @Override
    public void deleteFromViewedList(User user, Long contentId) {

    }

    @Override
    public List<Content> getUserViewedList(User user) {
        return null;
    }

    @Override
    public Optional<Long> searchContentInViewedList(User user, Long contentId) {
        return Optional.empty();
    }

    @Override
    public List<Long> getUserViewedListContent(User user) {
        return null;
    }

    @Override
    public void promoteUser(User userId) {

    }
}
