package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.PageWrapper;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.CommentDao;
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
public class CommentServiceImpl implements CommentService{
    private final CommentDao commentDao;
    private final EmailService emailService;
    @Autowired
    private MessageSource messageSource;
    private final Locale locale = LocaleContextHolder.getLocale();
    @Autowired
    public CommentServiceImpl(final CommentDao commentDao, final EmailService emailService){
        this.commentDao = commentDao;
        this.emailService = emailService;
    }
    @Override
    public Comment addComment(Review review, User user, String text) {
        Comment comment = null;
        try {
            comment=commentDao.addComment(review,user,text);
            if(!review.getUser().getUserName().equals(user.getUserName())) {
                Map<String, Object> mailVariables = new HashMap<>();
                mailVariables.put("to", review.getUser().getEmail());
                mailVariables.put("userName", review.getUser().getUserName());
                mailVariables.put("reviewComment", review.getName());
                mailVariables.put("contentURL", "http://pawserver.it.itba.edu.ar/paw-2022b-3/content/" + review.getContent().getType() + "/" + review.getContent().getId());
                mailVariables.put("comment", text);
                emailService.sendMail("addedComment", messageSource.getMessage("Mail.CommentAdded", new Object[]{}, locale), mailVariables, locale);
            }
        } catch (MessagingException e) {
        }
        return comment;
    }

    @Override
    public void deleteComment(Comment comment) {
        commentDao.deleteComment(comment);
    }

    @Override
    public Optional<Comment> getComment(Long id) {
        return commentDao.getComment(id);
    }

    @Override
    public Set<Comment> getReviewComments(Long reviewId){
        return commentDao.getReviewComment(reviewId);
    }

    @Override
    public List<Comment> getReviewComments(Review review){
        List<Comment> comments = new ArrayList<>(review.getComments());
        if(comments.size() == 0){
            return Collections.emptyList();
        }
        return comments;
    }
}
