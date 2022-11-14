package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;

import java.util.List;

public interface PaginationService {
    <T> List<T> pagePagination(List<T> list, int page,final int pageSize);
    <T> List<T> infiniteScrollPagination(List<T> objectList, int page,final int pageSize);
    List<Content> contentPagination(List<Content> contentList, int page);
    int amountOfContentPages(int contentListSize,final int pageSize);

    List<Review> reviewPagination(List<Review> reviewList, int page);
    <T> List<T> reportPagination(List<T> objectList, int page);
    int amountOfReviewPages(int reviewListSize);

    boolean checkPagination(int listSize, int page,final int pageSize);

}
