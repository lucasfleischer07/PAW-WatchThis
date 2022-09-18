package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Review;
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
    public void editReview(String newDesc, Long reviewId, String typeOfEdit) {
        reviewDao.editReview(newDesc, reviewId, typeOfEdit);
    }
    @Override
    public List<Review> getAllUserReviews(String name) {
        return reviewDao.getAllUserReviews(name);
    }

    @Override
    public Optional<Review> findById(Long reviewId) {return reviewDao.findById(reviewId);}
}
