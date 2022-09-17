package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Review;

import java.util.List;

public interface ReviewService {

    void addReview(Review review);          //No se si retornar bool

    List<Review> getAllReviews(Long id);
    void deleteReview(Long id);
    void editReview(String newDesc, Long id, String typeOfEdit);
    List <Review> getAllUserReviews(String username);
}
