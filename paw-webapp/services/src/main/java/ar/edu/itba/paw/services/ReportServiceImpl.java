package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistance.CommentDao;
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
public class ReportServiceImpl implements ReportService{
    private final ReportDao reportDao;
    private final ReviewDao reviewDao;
    private final CommentDao commentDao;
    private final EmailService emailService;
    @Autowired
    private MessageSource messageSource;
    private final Locale locale = LocaleContextHolder.getLocale();


    @Autowired
    public ReportServiceImpl(final ReportDao reportDao, ReviewDao reviewDao, CommentDao commentDao, final EmailService emailService){
        this.reportDao= reportDao;
        this.reviewDao = reviewDao;
        this.commentDao = commentDao;
        this.emailService = emailService;
    }
    @Override
    public void delete(Object reviewOrComment, Set<CommentReport> reasonsOfDelete) {
        String reasons = "";
        if(reasonsOfDelete != null) {
            for(CommentReport string : reasonsOfDelete) {
                switch (string.getReportReason().toString()) {
                    case "Spam":
                        reasons = reasons + messageSource.getMessage("Report.Spam", new Object[]{}, locale) + ", ";
                        break;
                    case "Insult":
                        reasons = reasons + messageSource.getMessage("Report.Insult", new Object[]{}, locale) + ", ";
                        break;
                    case "Inappropriate":
                        reasons = reasons + messageSource.getMessage("Report.Inappropriate", new Object[]{}, locale) + ", ";
                        break;
                    case "Unrelated":
                        reasons = reasons + messageSource.getMessage("Report.Unrelated", new Object[]{}, locale) + ", ";
                        break;
                    case "Other":
                        reasons = reasons + messageSource.getMessage("Report.Other", new Object[]{}, locale) + ", ";
                        break;
                    default:
                        return;
                }
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
            mailVariables.put("profileURL", "http://pawserver.it.itba.edu.ar/paw-2022b-3/user/profile/" + comment.getUser().getId());

            if(!Objects.equals(reason, "")){
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
            mailVariables.put("profileURL", "http://pawserver.it.itba.edu.ar/paw-2022b-3/user/profile/" + deletedReview.getUser().getId());
            if(!Objects.equals(reasonsOfDelete, "")) {
                mailVariables.put("reasonsOfDelete", reasonsOfDelete);
            }else{
                mailVariables.put("reasonsOfDelete", messageSource.getMessage("Mail.ReviewDeleteGeneralReason", new Object[]{}, locale));
            }
            emailService.sendMail("deleteReviewReported", messageSource.getMessage("Mail.ReviewDeleted", new Object[]{}, locale), mailVariables, locale);

        }catch (MessagingException e){

        }

    }

    @Override
    public void removeReports(String type, Long contentId) {
        if(Objects.equals(type, "review")) {
            reportDao.removeReports(reviewDao.findById(contentId).orElseThrow(IllegalArgumentException::new));
        } else {
            reportDao.removeReports(commentDao.getComment(contentId).orElseThrow(IllegalArgumentException::new));
        }
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
                return;
        }
    }


    private void reportReview(Review review, User reporterUser, ReportReason reasons){
        reportDao.addReport(review,reporterUser,reasons);
    }

    private void reportComment(Comment comment,User reporterUser,ReportReason reasons){
        reportDao.addReport(comment,reporterUser,reasons);
    }

    @Override
    public PageWrapper<ReviewReport> getReportedReviews(ReportReason reason,int page,int pageSize) {
        return reason==null ? reportDao.getReportedReviews(page,pageSize) : reportDao.getReportedReviewsByReason(reason,page,pageSize);
    }

    @Override
    public PageWrapper<CommentReport> getReportedComments(ReportReason reason,int page,int pageSize) {
        return reason==null ? reportDao.getReportedComments(page,pageSize) : reportDao.getReportedCommentsByReason(reason,page,pageSize);
    }
}
