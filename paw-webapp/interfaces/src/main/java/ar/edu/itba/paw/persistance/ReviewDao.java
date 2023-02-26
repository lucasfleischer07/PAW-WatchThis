package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.*;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    Optional<Review> addReview(String name,String description,int rating,String type,User creator,Content content);
    PageWapper<Review> getAllReviews(Content content,int page,int pageSize);
    void deleteReview(Long reviewId);
    Optional<Review> findById(Long reviewId);
    void updateReview(String name, String description, int rating,Long id);
    PageWapper<Review> getAllUserReviews(User user, int page, int pageSize);
    void thumbUpReview(Review review,User user);
    void thumbDownReview(Review review,User user);
    Optional<Review> getReview(Long reviewId);


}
