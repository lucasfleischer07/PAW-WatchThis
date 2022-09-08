package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Movie;
import ar.edu.itba.paw.models.Review;

import java.util.List;

public interface ReviewDao {

    void addReview(Review review);
    List<Review> getAllReviews(String type, Long id);
    void deleteReview(Long id);
    void editReview(String newDesc, Long id, String typeOfEdit);

}
