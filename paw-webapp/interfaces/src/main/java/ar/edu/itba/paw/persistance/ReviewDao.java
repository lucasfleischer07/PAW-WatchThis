package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    void addReview(Review review);
    List<Review> getAllReviews(Long contentId);
    void deleteReview(Long reviewId);
    Optional<Review> findById(Long reviewId);
    void updateReview(String name, String description, Integer rating, Long id);
    List<Review> getAllUserReviews(String username);

}
