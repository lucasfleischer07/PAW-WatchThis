package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    void addReview(Review review);          //No se si retornar bool
    List<Review> getAllReviews(Long contentId);
    void deleteReview(Long reviewId);
    Optional<Review> findById(Long reviewId);
    void updateReview(String name, String description, Integer rating, Long id);
    List<Review> getAllUserReviews(String username);
    List<Review> sortReviews(User user,List<Review> reviewList);
}
