package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ReviewService {

    void addReview(String name,String description,int rating,String type,User creator,Content content);          //No se si retornar bool
    List<Review> getAllReviews(Content content);
    void deleteReview(Long reviewId);
    Optional<Review> findById(Long reviewId);
    void updateReview(String name, String description, Integer rating, Long id);
    List<Review> getAllUserReviews(User user);
    List<Review> sortReviews(User user,List<Review> reviewList);
    void thumbUpReview(Review review,User user);
    void thumbDownReview(Review review,User user);
    Optional<Review> getReview(Long reviewId);
    void userLikeAndDislikeReviewsId(Set<Reputation> reputationList);
    List<Long> getUserLikeReviews();
    List<Long> getUserDislikeReviews();



}
