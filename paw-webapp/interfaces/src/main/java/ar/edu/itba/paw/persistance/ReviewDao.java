package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    void addReview(Review review);
    List<Review> getAllReviews(Long contentId);
    void deleteReview(Long reviewId);
    void editReview(String newDesc, Long reviewId, String typeOfEdit);
    List <Review> getAllUserReviews(String username);
    Optional<Review> findById(Long reviewId);

}
