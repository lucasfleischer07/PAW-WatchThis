package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaginationServiceImpl implements PaginationService{

    private static final int CONTENT_AMOUNT = 18;
    private static final int REVIEW_AMOUNT = 3;

    @Override
    public List<Content> contentPagination(List<Content> contentList, int page) {
        if(contentList.size() < (page)*CONTENT_AMOUNT) {       //Si no llega a completar la pagina entera, que sirva los que pueda
            return contentList.subList((page-1)*CONTENT_AMOUNT,contentList.size());
        } else {
            return contentList.subList((page - 1) * CONTENT_AMOUNT, (page - 1) * CONTENT_AMOUNT + CONTENT_AMOUNT);
        }
    }

    @Override
    public int amountOfContentPages(int contentListSize,final int pageSize) {
        return (int)Math.ceil((double) contentListSize/(double)pageSize);
    }

    @Override
    public List<Review> reviewPagination(List<Review> reviewList, int page) {
        if(reviewList.size() >= page*REVIEW_AMOUNT) {
            return reviewList.subList(0, page * REVIEW_AMOUNT);
        } else {
            return reviewList.subList(0, reviewList.size());
        }
    }

    @Override
    public <T> List<T> pagePagination(List<T> list, int page,final int pageSize) {
        if(list.size() < (page)*pageSize) {       //Si no llega a completar la pagina entera, que sirva los que pueda
            return list.subList((page-1)*pageSize,list.size());
        } else {
            return list.subList((page - 1) * pageSize, (page) * pageSize);
        }
    }

    @Override
    public <T> List<T> infiniteScrollPagination(List<T> list, int page,final int pageSize) {
        if(list.size() >= page*pageSize) {
            return list.subList(0, page * pageSize);
        } else {
            return list.subList(0, list.size());
        }
    }

    @Override
    public <T> List<T> reportPagination(List<T> objectList, int page) {
        if(objectList.size() >= page*REVIEW_AMOUNT) {
            return objectList.subList(0, page * REVIEW_AMOUNT);
        } else {
            return objectList.subList(0, objectList.size());
        }
    }

    @Override
    public int amountOfReviewPages(int reviewListSize) {
        return (int) Math.ceil((double)reviewListSize/(double)REVIEW_AMOUNT);
    }

    @Override
    public boolean checkPagination(int listSize, int page,final int pageSize) {
        if(page==1 && listSize==0)
            return false;
        return (page-1)*pageSize >= listSize;
    }

}
