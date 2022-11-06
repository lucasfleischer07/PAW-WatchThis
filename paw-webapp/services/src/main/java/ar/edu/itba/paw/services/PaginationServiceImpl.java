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
        if(contentList == null)
            return null;
        if(contentList.size() < (page)*CONTENT_AMOUNT) {       //Si no llega a completar la pagina entera, que sirva los que pueda
            return contentList.subList((page-1)*CONTENT_AMOUNT,contentList.size());
        } else {
            return contentList.subList((page - 1) * CONTENT_AMOUNT, (page - 1) * CONTENT_AMOUNT + CONTENT_AMOUNT);
        }
    }

    @Override
    public int amountOfPages(int contentListSize) {
        return (int)Math.ceil((double) contentListSize/(double)CONTENT_AMOUNT);
    }

    @Override
    public List<Review> reviewPagination(List<Review> reviewList, int page) {
        if(reviewList == null)
            return null;
        if(reviewList.size() >= page*REVIEW_AMOUNT) {
            return reviewList.subList(0, page * REVIEW_AMOUNT);
        } else {
            return reviewList.subList(0, reviewList.size());
        }
    }

    @Override
    public boolean checkPagination(int listSize, int page) {
        if(page==1 && listSize==0)
            return false;
        return (page-1)*CONTENT_AMOUNT >= listSize;
    }

}
