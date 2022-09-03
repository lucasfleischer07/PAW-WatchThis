package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Movie;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Serie;
import ar.edu.itba.paw.persistance.ReviewDao;
import ar.edu.itba.paw.persistance.SerieDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Review> getAllReviews(String type, Long id) {
        return reviewDao.getAllReviews(type,id);
    }
}
