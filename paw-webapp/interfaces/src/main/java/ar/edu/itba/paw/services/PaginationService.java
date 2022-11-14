package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;

import java.util.List;

public interface PaginationService {
    <T> List<T> pagePagination(List<T> list, int page,final int pageSize);
    <T> List<T> infiniteScrollPagination(List<T> objectList, int page,final int pageSize);
    int amountOfContentPages(int contentListSize,final int pageSize);
    boolean checkPagination(int listSize, int page,final int pageSize);

}
