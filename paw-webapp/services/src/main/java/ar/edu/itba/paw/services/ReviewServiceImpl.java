package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class ReviewServiceImpl implements ReviewService{
    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(final ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public void addReview(String name,String description,int rating,String type,User creator,Content content) {
        reviewDao.addReview(name, description, rating, type, creator, content);
    }

    @Override
    public List<Review> getAllReviews(Content content) {
        return reviewDao.getAllReviews(content);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewDao.deleteReview(reviewId);
    }

    @Override
    public Optional<Review> findById(Long reviewId) {return reviewDao.findById(reviewId);}

    @Override
    public List<Review> getAllUserReviews(User user){return reviewDao.getAllUserReviews(user);}
    
    @Override
    public void updateReview(String name, String description, Integer rating, Long id) {
        reviewDao.updateReview(name, description, rating, id);
    }

    @Override
    public List<Review> sortReviews(User user, List<Review> reviewList){
        if(user!=null){
            for (Review review:reviewList) {
                if(review.getCreator().getUserName().equals(user.getUserName())){
                    reviewList.remove(review);
                    reviewList.add(0,review);
                    break;
                }
            }
        }
        return reviewList;
    }

    @Override
    public void thumbUpReview(Review review,User user) {
        reviewDao.thumbUpReview(review,user);
    }

    @Override
    public void thumbDownReview(Review review,User user) {
        reviewDao.thumbDownReview(review,user);
    }

    @Override
    public Optional<Review> getReview(Long reviewId) {
        return reviewDao.getReview(reviewId);
    }

}
