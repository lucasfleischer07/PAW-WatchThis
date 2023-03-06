package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.PageWrapper;
import ar.edu.itba.paw.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
@Transactional
public class UserJpaDao implements UserDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> create(String userEmail, String userName, String password) {
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
        return query.getResultList().stream().findFirst();
    }

    @Override
    public void setPassword(String password, User user) {
        user.setPassword(password);
        em.merge(user);

    }

    @Override
    public void setProfilePicture(byte[] profilePicture, User user) {
        user.setImage(profilePicture);
        em.merge(user);
    }

    @Override
    public void addToWatchList(User user, Content toAdd) {
        List<Content> watchlist=user.getWatchlist();
        boolean found=false;
        for (Content content:watchlist) {
            if (content.getId()==toAdd.getId()){
                found=true;
                break;
            }
        }
        if(!found){
            user.getWatchlist().add(toAdd);
            em.merge(user);
        }
    }

    @Override
    public void deleteFromWatchList(User user, Content toDelete) {
        Query query=em.createNativeQuery("DELETE from userwatchlist where userid = :userid AND contentid= :contentid");
        query.setParameter("userid",user.getId());
        query.setParameter("contentid",toDelete.getId());
        query.executeUpdate();
    }

    @Override
    public PageWrapper<Content> getWatchList(User user, int page, int pageSize) {
        List<Content> contentList = user.getWatchlist();
        int pageAmount= PageWrapper.calculatePageAmount(contentList.size(),pageSize);
        PageWrapper<Content> pageWrapper;
        if( page < pageAmount){
            pageWrapper = new PageWrapper<>(page,pageAmount,pageSize,contentList.subList((page-1)*pageSize,page * pageSize));
        }else{
            pageWrapper = new PageWrapper<>(page,pageAmount,pageSize,contentList.subList((page-1)*pageSize,(page-1) * pageSize + contentList.size()% pageSize ));
        }

        return pageWrapper;
    }

    @Override
    public Optional<Long> searchContentInWatchList(User user, Long contentId) {
        for (Content content:user.getWatchlist()
             ) {
            if (content.getId()==contentId)
                return Optional.of(contentId);
        };
        return Optional.of(-1L);
    }

    @Override
    public void addToViewedList(User user, Content toAdd) {
        List<Content> viewedList=user.getViewedList();
        boolean found=false;
        for (Content content:viewedList
        ) {
            if (content.getId()==toAdd.getId()){
                found=true;
                break;
            }
        }
        if(!found){
            user.getViewedList().add(toAdd);
            em.merge(user);
        }
    }

    @Override
    public void deleteFromViewedList(User user, Content toDelete) {
        Query query=em.createNativeQuery("DELETE from userviewedlist where userid = :userid AND contentid= :contentid");
        query.setParameter("userid",user.getId());
        query.setParameter("contentid",toDelete.getId());
        query.executeUpdate();
    }

    @Override
    public PageWrapper<Content> getUserViewedList(User user,int page,int pageSize) {
        List<Content> contentList = user.getViewedList();
        int pageAmount= PageWrapper.calculatePageAmount(contentList.size(),pageSize);
        PageWrapper<Content> pageWrapper;
        if( page < pageAmount){
            pageWrapper = new PageWrapper<>(page,pageAmount,pageSize,contentList.subList((page-1)*pageSize,page * pageSize));
        }else{
            pageWrapper = new PageWrapper<>(page,pageAmount,pageSize,contentList.subList((page-1)*pageSize,(page-1) * pageSize + contentList.size()% pageSize ));
        }

        return pageWrapper;
    }

    @Override
    public Optional<Long> searchContentInViewedList(User user, Long contentId) {
        for (Content content : user.getViewedList()) {
            if (content.getId()==contentId)
                return Optional.of(contentId);
        };
        return Optional.of(-1L);
    }


    @Override
    public void promoteUser(User user) {
        Optional<User> maybeUser= findById(user.getId());
        if(maybeUser.isPresent()){
            maybeUser.get().setRole("admin");
            em.merge(maybeUser.get());
        }
    }
}
