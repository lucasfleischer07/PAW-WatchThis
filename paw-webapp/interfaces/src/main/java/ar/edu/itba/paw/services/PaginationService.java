package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;

import java.util.List;

public interface PaginationService {

    List<Content> contentPagination(List<Content> contentList, int page);
    int amountOfContentPages(int contentListSize);

    List<Review> reviewPagination(List<Review> reviewList, int page);
    List<Object> reportPagination(List<Object> objectList, int page);
    int amountOfReviewPages(int reviewListSize);

    boolean checkPagination(int listSize, int page);

}
