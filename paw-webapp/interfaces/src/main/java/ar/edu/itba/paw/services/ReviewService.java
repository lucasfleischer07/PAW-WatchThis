package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    void addReview(String name,String description,int rating,String type,User creator,Content content);          //No se si retornar bool
    List<Review> getAllReviews(Content content);
    void deleteReview(Long reviewId);
    Optional<Review> findById(Long reviewId);
    void updateReview(String name, String description, Integer rating, Long id);
    List<Review> getAllUserReviews(User user);

}
