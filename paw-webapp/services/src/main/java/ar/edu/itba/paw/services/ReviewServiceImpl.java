package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistance.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewDao reviewDao;
    private List<Long> userLikeReviews = new ArrayList<>();
    private List<Long> userDislikeReviews = new ArrayList<>();

    @Autowired
    public ReviewServiceImpl(final ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public void addReview(String name,String description,int rating,String type,User creator,Content content) {
        reviewDao.addReview(name, description, rating, type, creator, content);
    }

    @Override
    public PageWrapper<Review> getAllReviews(Content content, int page, int pageSize) {
        return reviewDao.getAllReviews(content,page,pageSize);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewDao.deleteReview(reviewId);
    }


    @Override
    public Optional<Review> findById(Long reviewId) {return reviewDao.findById(reviewId);}

    @Override
    public PageWrapper<Review> getAllUserReviews(User user, int page, int pageSize){return reviewDao.getAllUserReviews(user,page,pageSize);}
    
    @Override
    public void updateReview(String name, String description, Integer rating, Long id) {
        reviewDao.updateReview(name, description, rating, id);
    }

    @Override
    public List<Review> sortReviews(User user, List<Review> reviewList){
        List<Review> auxList = new ArrayList<>();
        if(user!=null){
            for (Review review:reviewList) {
                if(review.getUser().getUserName().equals(user.getUserName())){
                    auxList.add(0,review);
                }else{
                    auxList.add(review);
                }
            }
            reviewList=auxList;
        }

        return reviewList;
    }

    @Override
    public void thumbUpReview(Review review,User user) {
        reviewDao.thumbUpReview(review,user);
    }

    @Override
    public void thumbDownReview(Review review,User user) {
        reviewDao.thumbDownReview(review,user);
    }

    @Override
    public Optional<Review> getReview(Long reviewId) {
        return reviewDao.getReview(reviewId);
    }

    @Override
    public void userLikeAndDislikeReviewsId(Set<Reputation> reputationList) {
        userLikeReviews.clear();
        userDislikeReviews.clear();
        for(Reputation reputation : reputationList) {
            if(reputation.isUpvote()) {
                userLikeReviews.add(reputation.getReview().getId());
            } else if(reputation.isDownvote()) {
                userDislikeReviews.add(reputation.getReview().getId());
            }
        }
    }

    @Override
    public List<Long> getUserLikeReviews() {
        return userLikeReviews;
    }
    @Override
    public List<Long> getUserDislikeReviews() {
        return userDislikeReviews;
    }



}
