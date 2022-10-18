package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    Optional<Review> addReview(String name,String description,int rating,String type,User creator,Content content);
    List<Review> getAllReviews(Content content);
    void deleteReview(Long reviewId);
    Optional<Review> findById(Long reviewId);
    void updateReview(String name, String description, int rating,Long id);
    List<Review> getAllUserReviews(User user);

}
