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
    private Set<Review> userLikeReviews = new HashSet<>();
    private Set<Review> userDislikeReviews = new HashSet<>();

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
    public PageWrapper<Review> getAllReviewsSorted(Content content, int page, int pageSize,long userId){
        return reviewDao.getAllUserReviewsSorted(content, page, pageSize, userId);
    }


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
                userLikeReviews.add(reputation.getReview());
            } else if(reputation.isDownvote()) {
                userDislikeReviews.add(reputation.getReview());
            }
        }
    }

    @Override
    public Set<Review> userLikeReviews(Set<Reputation> reputationList) {
        Set<Review> userLikeReview = new HashSet<>();
        for(Reputation reputation : reputationList) {
            if(reputation.isUpvote()) {
                userLikeReview.add(reputation.getReview());
            }
        }
        return userLikeReview;
    }

    @Override
    public Set<Review> userDislikeReviews(Set<Reputation> reputationList) {
        Set<Review> userDislikeReview = new HashSet<>();
        for(Reputation reputation : reputationList) {
            if(reputation.isDownvote()) {
                userDislikeReview.add(reputation.getReview());
            }
        }
        return userDislikeReview;

    }

    @Override
    public Set<Review> getUserLikeReviews() {
        return userLikeReviews;
    }
    @Override
    public Set<Review> getUserDislikeReviews() {
        return userDislikeReviews;
    }

    @Override
    public boolean deleteThumbDownReview(Review review,User user){
        for (Reputation reputation:review.getUserVotes()) {
            if(reputation.getUser().getId()== user.getId()) {
                if(reputation.isUpvote()){
                    return false;
                }
                else {
                    reviewDao.deleteVote(review,user);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteThumbUpReview(Review review,User user) {
        for (Reputation reputation:review.getUserVotes()) {
            if(reputation.getUser().getId()== user.getId()) {
                if(reputation.isDownvote()){
                    return false;
                }
                else {
                    reviewDao.deleteVote(review,user);
                    return true;
                }
            }
        }
        return false;
    }

}
