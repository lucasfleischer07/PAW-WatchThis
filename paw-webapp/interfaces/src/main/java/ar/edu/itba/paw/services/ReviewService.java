package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Movie;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Serie;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    void addReview(Review review);          //No se si retornar bool

    List<Review> getAllReviews(String type, Long id);
    void deleteReview(Long id);
    void editReview(String newDesc, Long id, String typeOfEdit);
}
