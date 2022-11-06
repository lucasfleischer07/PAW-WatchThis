package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistance.ReportDao;
import ar.edu.itba.paw.persistance.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.*;

@Transactional
@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewDao reviewDao;
    private final ReportDao reportDao;
    private final EmailService emailService;

    @Autowired
    private MessageSource messageSource;
    private final Locale locale = LocaleContextHolder.getLocale();

    private List<Long> userLikeReviews = new ArrayList<>();
    private List<Long> userDislikeReviews = new ArrayList<>();

    @Autowired
    public ReviewServiceImpl(final ReviewDao reviewDao,final EmailService emailService,final ReportDao reportDao) {
        this.emailService=emailService;
        this.reviewDao = reviewDao;
        this.reportDao = reportDao;
    }

    @Override
    public void addReview(String name,String description,int rating,String type,User creator,Content content) {
        reviewDao.addReview(name, description, rating, type, creator, content);
    }

    @Override
    public List<Review> getAllReviews(Content content) {
        return reviewDao.getAllReviews(content);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewDao.deleteReview(reviewId);
    }

    @Override
    public void adminDeleteReview(Review deletedReview,String reasonsOfDelete) {
        try{
            reviewDao.deleteReview(deletedReview.getId());
            Map<String, Object> mailVariables = new HashMap<>();
            mailVariables.put("to",deletedReview.getCreator().getEmail());
            mailVariables.put("userName", deletedReview.getCreator().getUserName());
            mailVariables.put("deletedReview", deletedReview.getName());
            if(reasonsOfDelete!=null){
                mailVariables.put("reasonsOfDelete", reasonsOfDelete);
            }else{
                mailVariables.put("reasonsOfDelete", messageSource.getMessage("Mail.ReviewDeleteGeneralReason", new Object[]{}, locale));
            }
            emailService.sendMail("deleteReviewReported", messageSource.getMessage("Mail.ReviewDeleted", new Object[]{}, locale), mailVariables, locale);

        }catch (MessagingException e){

        }

    }

    @Override
    public Optional<Review> findById(Long reviewId) {return reviewDao.findById(reviewId);}

    @Override
    public List<Review> getAllUserReviews(User user){return reviewDao.getAllUserReviews(user);}
    
    @Override
    public void updateReview(String name, String description, Integer rating, Long id) {
        reviewDao.updateReview(name, description, rating, id);
    }

    @Override
    public List<Review> sortReviews(User user, List<Review> reviewList){
        if(user!=null){
            for (Review review:reviewList) {
                if(review.getCreator().getUserName().equals(user.getUserName())){
                    reviewList.remove(review);
                    reviewList.add(0,review);
                    break;
                }
            }
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

    @Override
    public void reportReview(Review review,User reporterUser,ReportReason reasons,String description){
        try{
            reportDao.addReport(review,reasons,description);
            Map<String, Object> mailVariables = new HashMap<>();
            mailVariables.put("to",reporterUser.getEmail());    // Email del creador del comentario
            mailVariables.put("userName", reporterUser.getUserName());  // userName del creador del comentario
            mailVariables.put("reportedReview", review.getName());  // Esto seria la review, onda lo que decia la review
            emailService.sendMail("reportReview", messageSource.getMessage("Mail.ReviewReported", new Object[]{}, locale), mailVariables, locale);
        }catch (MessagingException e){
            //algo
        }

    }
    @Override
    public void reportComment(Comment Comment,User reporterUser,ReportReason reasons,String description){
        try{
            reportDao.addReport(Comment,reasons,description);
            Map<String, Object> mailVariables = new HashMap<>();
            mailVariables.put("to",reporterUser.getEmail());    // Email del creador del comentario
            mailVariables.put("userName", reporterUser.getUserName());  // userName del creador del comentario
            mailVariables.put("reportedReview", Comment.getText());  // Esto seria la review, onda lo que decia la review
            emailService.sendMail("reportComment", messageSource.getMessage("Mail.CommentReported", new Object[]{}, locale), mailVariables, locale);
        }catch (MessagingException e){
            //algo
        }
    }

    @Override
    public void addComment(Review review, User user, String text) {
        reviewDao.addComment(review,user,text);
    }

    @Override
    public void deleteComment(Comment comment) {
        reviewDao.deleteComment(comment);
    }

    @Override
    public void adminDeleteComment(Comment comment,String reason){
        try {
            reviewDao.deleteComment(comment);
            Map<String, Object> mailVariables = new HashMap<>();
            mailVariables.put("to", comment.getUser().getEmail());
            mailVariables.put("userName", comment.getUser().getUserName());
            mailVariables.put("deletedComment", comment.getText());
            if(reason!=null){
                mailVariables.put("reasonsOfDelete", reason);
            }else{
                mailVariables.put("reasonsOfDelete", messageSource.getMessage("Mail.CommentDeleteGeneralReason", new Object[]{}, locale));
            }
            emailService.sendMail("deleteCommentReported", messageSource.getMessage("Mail.CommentDeleted", new Object[]{}, locale), mailVariables, locale);
        } catch (MessagingException e) {
            //algo
        }


    }
}
