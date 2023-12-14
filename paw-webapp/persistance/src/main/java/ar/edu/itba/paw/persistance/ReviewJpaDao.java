package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
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

    private PageWrapper<Review> genericGetAllReviews(List<Review> reviews,int page,int pageSize){
        long totalPages = PageWrapper.calculatePageAmount(reviews.size(),pageSize);
        if(page == 1 && reviews.size() == 0){
            return new PageWrapper<Review>(page,totalPages,pageSize,Collections.emptyList(),0);
        }
        if(page > totalPages || page <= 0){
            return new PageWrapper<Review>(page,totalPages,pageSize,null,reviews.size());
        }
        if(page < totalPages || (page == totalPages && reviews.size() % pageSize == 0)){
            return new PageWrapper<Review>(page,totalPages,pageSize,reviews.subList((page-1)*pageSize,page * pageSize),reviews.size());
        }
        return new PageWrapper<Review>(page,totalPages,pageSize,reviews.subList((page-1)*pageSize,(page-1)*pageSize + (reviews.size() % pageSize)),reviews.size());
    }

    @Override
    public PageWrapper<Review> getAllReviews(Content content, int page, int pageSize){
        List<Review> reviews = content.getContentReviews();
        return genericGetAllReviews(reviews,page,pageSize);
    }

    @Override
    public PageWrapper<Review> getAllUserReviewsSorted(Content content, int page, int pageSize, long userId){
        List<Review> reviews = content.getContentReviews();
        Review userReview = reviews.stream()
                .filter(review -> review.getUser().getId() == userId) // Reemplaza con tu condición
                .findFirst()
                .orElse(null);
        if(userReview == null){
            return genericGetAllReviews(reviews,page,pageSize);
        }
        PageWrapper<Review> reviewPageWrapper = genericGetAllReviews(reviews,page,pageSize);
        if(reviewPageWrapper.getPageContent().contains(userReview)){
            reviewPageWrapper.getPageContent().remove(userReview);
        }else{
            reviewPageWrapper.getPageContent().remove((int)reviewPageWrapper.getPageAmount() - 1);
        }
        reviewPageWrapper.getPageContent().add(0,userReview);
        return reviewPageWrapper;
    }


    @Override
    public void deleteReview(Long reviewId) {
        Review toDelete=findById(reviewId).get();
        User user=toDelete.getUser();
        Content content=toDelete.getContent();
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
    public PageWrapper<Review> getAllUserReviews(User user, int page, int pageSize) {
        List<Review> reviews = user.getUserReviews();
        return genericGetAllReviews(reviews,page,pageSize);
    }

    @Override
    public void thumbUpReview(Review review,User user) {
        for (Reputation reputation:review.getUserVotes()) {
            if(reputation.getUser().getId()== user.getId()) {
                if(reputation.isUpvote()){
                    em.remove(reputation);

                } else{
                    reputation.setUpvote(true);
                    reputation.setDownvote(false);
                    em.merge(reputation);
                }
                return;
            }
        }
        Reputation toAdd=new Reputation(user,review,true);
        em.persist(toAdd);
    }

    @Override
    public void thumbDownReview(Review review,User user) {
        for (Reputation reputation:review.getUserVotes()) {
            if(reputation.getUser().getId()== user.getId()) {
                if(reputation.isDownvote()){
                    em.remove(reputation);

                } else{
                    reputation.setDownvote(true);
                    reputation.setUpvote(false);
                    em.merge(reputation);
                }
                return;
            }
        }
        Reputation toAdd=new Reputation(user,review,false);
        em.persist(toAdd);
    }

    @Override
    public Optional<Review> getReview(Long reviewId) {
        return Optional.ofNullable(em.find(Review.class, reviewId));
    }





}
