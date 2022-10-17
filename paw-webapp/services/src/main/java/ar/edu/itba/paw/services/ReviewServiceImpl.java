package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{
    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(final ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public void addReview(Review review) {
        reviewDao.addReview(review);
    }

    @Override
    public List<Review> getAllReviews(Long contentId) {
        return reviewDao.getAllReviews(contentId);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewDao.deleteReview(reviewId);
    }

    @Override
    public Optional<Review> findById(Long reviewId) {return reviewDao.findById(reviewId);}

    @Override
    public List<Review> getAllUserReviews(String username){return reviewDao.getAllUserReviews(username);}
    
    @Override
    public void updateReview(String name, String description, Integer rating, Long id) {
        reviewDao.updateReview(name, description, rating, id);
    }

    @Override
    public List<Review> sortReviews(User user, List<Review> reviewList){
        if(user!=null){
            for (Review review:reviewList) {
                if(review.getUserName().equals(user.getUserName())){
                    reviewList.remove(review);
                    reviewList.add(0,review);
                    break;
                }
            }
        }
        return reviewList;
    }
}
