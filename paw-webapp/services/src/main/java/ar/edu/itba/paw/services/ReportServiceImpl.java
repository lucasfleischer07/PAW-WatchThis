package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistance.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.*;

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
    public void delete(Object reviewOrComment, Set<CommentReport> reasonsOfDelete) {
        String reasons = "";
        if(reasonsOfDelete != null) {
            for(CommentReport string : reasonsOfDelete) {
                reasons = reasons + ", " + string.toString();
            }
        }
        if(reviewOrComment instanceof Review)
            adminDeleteReview((Review) reviewOrComment,reasons);
        else if(reviewOrComment instanceof Comment){
            adminDeleteComment((Comment) reviewOrComment,reasons);
        } else throw new IllegalArgumentException();
    }

    private void adminDeleteComment(Comment comment, String reason){
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

    private void adminDeleteReview(Review deletedReview, String reasonsOfDelete) {
        try{
            reportDao.delete(deletedReview);
            Map<String, Object> mailVariables = new HashMap<>();
            mailVariables.put("to",deletedReview.getUser().getEmail());
            mailVariables.put("userName", deletedReview.getUser().getUserName());
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
    public void addReport(Object reviewOrComment,User reporterUser, String reason) {
        switch (reason) {
            case "Spam":
                if (reviewOrComment instanceof Review)
                    reportReview((Review) reviewOrComment, reporterUser, ReportReason.Spam);
                else if (reviewOrComment instanceof Comment) {
                    reportComment((Comment) reviewOrComment, reporterUser, ReportReason.Spam);
                } else {
                    throw new IllegalArgumentException();
                }
                break;
            case "Insult":
                if (reviewOrComment instanceof Review)
                    reportReview((Review) reviewOrComment, reporterUser, ReportReason.Insult);
                else if (reviewOrComment instanceof Comment) {
                    reportComment((Comment) reviewOrComment, reporterUser, ReportReason.Insult);
                } else {
                    throw new IllegalArgumentException();
                }
                break;
            case "Inappropriate":
                if (reviewOrComment instanceof Review)
                    reportReview((Review) reviewOrComment, reporterUser, ReportReason.Inappropriate);
                else if (reviewOrComment instanceof Comment) {
                    reportComment((Comment) reviewOrComment, reporterUser, ReportReason.Inappropriate);
                } else {
                    throw new IllegalArgumentException();
                }
                break;
            case "Unrelated":
                if (reviewOrComment instanceof Review)
                    reportReview((Review) reviewOrComment, reporterUser, ReportReason.Unrelated);
                else if (reviewOrComment instanceof Comment) {
                    reportComment((Comment) reviewOrComment, reporterUser, ReportReason.Unrelated);
                } else {
                    throw new IllegalArgumentException();
                }
                break;
            case "Other":
                if (reviewOrComment instanceof Review)
                    reportReview((Review) reviewOrComment, reporterUser, ReportReason.Other);
                else if (reviewOrComment instanceof Comment) {
                    reportComment((Comment) reviewOrComment, reporterUser, ReportReason.Other);
                } else {
                    throw new IllegalArgumentException();
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
    }


    public void reportReview(Review review, User reporterUser, ReportReason reasons){
        try{
            User reportedUser=review.getUser();
            reportDao.addReport(review,reporterUser,reasons);
            Map<String, Object> mailVariables = new HashMap<>();
            mailVariables.put("to",reportedUser.getEmail());
            mailVariables.put("userName", reportedUser.getUserName());
            mailVariables.put("reportedReview", review.getName());
            emailService.sendMail("reportReview", messageSource.getMessage("Mail.ReviewReported", new Object[]{}, locale), mailVariables, locale);
        }catch (MessagingException e){
            //algo
        }

    }

    public void reportComment(Comment comment,User reporterUser,ReportReason reasons){
        try{
            User reportedUser=comment.getUser();
            reportDao.addReport(comment,reporterUser,reasons);
            Map<String, Object> mailVariables = new HashMap<>();
            mailVariables.put("to",reportedUser.getEmail());    // Email del creador del comentario
            mailVariables.put("userName", reportedUser.getUserName());  // userName del creador del comentario
            mailVariables.put("reportedComment", comment.getText());  // Esto seria la review, onda lo que decia la review
            emailService.sendMail("reportComment", messageSource.getMessage("Mail.CommentReported", new Object[]{}, locale), mailVariables, locale);
        }catch (MessagingException e){
            //algo
        }
    }

    @Override
    public List<ReviewReport> getReportedReviews(ReportReason reason) {
        return reason==null ? reportDao.getReportedReviews() : reportDao.getReportedReviewsByReason(reason);
    }

    @Override
    public List<CommentReport> getReportedComments(ReportReason reason) {
        return reason==null ? reportDao.getReportedComments() : reportDao.getReportedCommentsByReason(reason);
    }
}
