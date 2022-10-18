package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
@Transactional
public class ReviewJpaDao implements ReviewDao{
    @PersistenceContext
    private EntityManager em;
    @Override
    public Optional<Review> addReview(String name,String description,int rating,String type,User creator,Content content) {
        Review toAdd=new Review(type,name,description,rating,creator,content);
        em.persist(toAdd);
        creator.getUserReviews().add(toAdd);
        content.getContentReviews().add(toAdd);
        return Optional.of(toAdd);
    }

    @Override
    public List<Review> getAllReviews(Content content) {
        return content.getContentReviews();
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review toDelete=findById(reviewId).get();
        toDelete.getCreator().getUserReviews().remove(toDelete);
        toDelete.getContent().getContentReviews().remove(toDelete);
        em.remove(toDelete);
    }

    @Override
    public Optional<Review> findById(Long reviewId) {
        return Optional.ofNullable(em.find(Review.class,reviewId));
    }

    @Override
    public void updateReview(String name, String description, int rating, Long id) {
        Review toUpdate=findById(id).get();
        toUpdate.setName(name);
        toUpdate.setDescription(description);
        toUpdate.setRating(rating);
        em.merge(toUpdate);
    }

    @Override
    public List<Review> getAllUserReviews(User user) {
        return user.getUserReviews();
    }
}
