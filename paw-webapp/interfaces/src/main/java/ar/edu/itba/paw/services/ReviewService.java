package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    void addReview(Review review);          //No se si retornar bool
    List<Review> getAllReviews(Long contentId);
    void deleteReview(Long reviewId);
    void editReview(String newDesc, Long reviewId, String typeOfEdit);
    List <Review> getAllUserReviews(String username);
    Optional<Review> findById(Long reviewId);
}
