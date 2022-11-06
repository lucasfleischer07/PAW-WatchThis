package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;

import java.util.List;

public interface PaginationService {

    List<Content> contentPagination(List<Content> contentList, int page);
    int amountOfPages(int contentListSize);

    List<Review> reviewPagination(List<Review> reviewList, int page);

    boolean checkPagination(int listSize, int page);

}
