package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistance.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Transactional
@Service
public class ReportServiceImpl implements ReportService{
    private final ReportDao reportDao;
    private final EmailService emailService;
    @Autowired
    private MessageSource messageSource;
    private final Locale locale = LocaleContextHolder.getLocale();


    @Autowired
    public ReportServiceImpl(final ReportDao reportDao,final EmailService emailService){
        this.reportDao=reportDao;
        this.emailService=emailService;}
    @Override
    public void delete(Object reviewOrComment,String reasonsOfDelete) {
        if(reviewOrComment instanceof Review)
            adminDeleteReview((Review) reviewOrComment,reasonsOfDelete);
        else if(reviewOrComment instanceof Comment){
            adminDeleteComment((Comment) reviewOrComment,reasonsOfDelete);
        } else throw new IllegalArgumentException();
    }

    public void adminDeleteComment(Comment comment, String reason){
        try {
            reportDao.delete(comment);
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

    public void adminDeleteReview(Review deletedReview, String reasonsOfDelete) {
        try{
            reportDao.delete(deletedReview);
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
    public void removeReports(Object reviewOrComment) {
        reportDao.removeReports(reviewOrComment);
    }

    @Override
    public void addReport(Object reviewOrComment, ReportReason reason, String text) {
        if(reviewOrComment instanceof Review)
            reportReview((Review) reviewOrComment,((Review) reviewOrComment).getCreator(),reason,text);
        else if(reviewOrComment instanceof Comment){
            reportComment((Comment) reviewOrComment, ((Comment) reviewOrComment).getUser(),reason,text );
        } else throw new IllegalArgumentException();
    }


    public void reportReview(Review review, User reportedUser, ReportReason reasons, String description){
        try{
            reportDao.addReport(review,reasons,description);
            Map<String, Object> mailVariables = new HashMap<>();
            mailVariables.put("to",reportedUser.getEmail());    // Email del creador del comentario
            mailVariables.put("userName", reportedUser.getUserName());  // userName del creador del comentario
            mailVariables.put("reportedReview", review.getName());  // Esto seria la review, onda lo que decia la review
            emailService.sendMail("reportReview", messageSource.getMessage("Mail.ReviewReported", new Object[]{}, locale), mailVariables, locale);
        }catch (MessagingException e){
            //algo
        }

    }

    public void reportComment(Comment comment,User reportedUser,ReportReason reasons,String description){
        try{
            reportDao.addReport(comment,reasons,description);
            Map<String, Object> mailVariables = new HashMap<>();
            mailVariables.put("to",reportedUser.getEmail());    // Email del creador del comentario
            mailVariables.put("userName", reportedUser.getUserName());  // userName del creador del comentario
            mailVariables.put("reportedReview", comment.getText());  // Esto seria la review, onda lo que decia la review
            emailService.sendMail("reportComment", messageSource.getMessage("Mail.CommentReported", new Object[]{}, locale), mailVariables, locale);
        }catch (MessagingException e){
            //algo
        }
    }

    @Override
    public List<ReviewReport> getReportedReviews() {
        return reportDao.getReportedReviews();
    }

    @Override
    public List<CommentReport> getReportedComments() {
        return reportDao.getReportedComments();
    }
}
