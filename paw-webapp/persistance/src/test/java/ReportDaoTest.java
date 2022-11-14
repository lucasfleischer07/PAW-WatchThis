import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.ReportReason;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.persistance.*;
import config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;

import javax.sql.DataSource;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:report-dao-test.sql")
public class ReportDaoTest {
    private final Long commentId=1L;
    private final Long reviewId=3L;
    private final Long contentId=2L;
    private final Long reviewReportId=2L;
    private final Long commentReportId=2L;
    private final Long userId=2L;

    @Autowired
    private DataSource ds;
    @Autowired
    private ReportDao dao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ContentDao contentDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ReviewDao reviewDao;
    private JdbcTemplate jdbcTemplate;
    private Review testReview;
    private Comment testComment;
    private Content testContent;
    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.testComment=commentDao.getComment(commentId).get();
        this.testReview=reviewDao.getReview(reviewId).get();
        this.testContent=contentDao.findById(contentId).get();
    }

    @Test
    public void findReviewReportTest(){
        assertEquals(reviewReportId,(Long) dao.findReviewReport(reviewReportId).get().getId());
    }

    @Test
    public void findCommentReportTest(){
        assertEquals(commentReportId,(Long) dao.findCommentReport(commentReportId).get().getId());
    }

    @Test
    @Rollback
    public void testDeleteReview(){
        dao.delete(testReview);
        assertFalse(commentDao.getComment(commentId).isPresent());
        assertFalse(reviewDao.findById(reviewId).isPresent());
        assertFalse(dao.findReviewReport(reviewReportId).isPresent());
        assertFalse(dao.findCommentReport(commentReportId).isPresent());

    }

    @Test
    @Rollback
    public void testDeleteComment(){
        dao.delete(testComment);
        assertFalse(commentDao.getComment(commentId).isPresent());
    }

    @Test
    @Rollback
    public void testRemoveReviewReports(){
        dao.removeReports(testReview);
        assertFalse(dao.findReviewReport(reviewReportId).isPresent());
    }

    @Test
    @Rollback
    public void testRemoveCommentReports(){
        dao.removeReports(testComment);
        assertFalse(dao.findCommentReport(commentReportId).isPresent());
    }

    @Test
    public void getReportedReviews(){
        assertEquals(1,dao.getReportedReviews().size());
    }
    @Test
    public void getReportedComments(){
        assertEquals(1,dao.getReportedComments().size());
    }
    @Test
    public void getReportedReviewsByReason(){
        assertEquals(1,dao.getReportedReviewsByReason(ReportReason.Inappropriate).size());
        assertEquals(0,dao.getReportedReviewsByReason(ReportReason.Other).size());
    }
    @Test
    public void getReportedCommentsByReason(){
        assertEquals(1,dao.getReportedReviewsByReason(ReportReason.Other).size());
        assertEquals(0,dao.getReportedReviewsByReason(ReportReason.Inappropriate).size());
    }

    @Test
    public void addReviewReport(){
        dao.addReport(reviewDao.findById(reviewId).get(),userDao.findById(userId).get(),ReportReason.Inappropriate);
        assertEquals(2,reviewDao.findById(reviewId).get().getReports().size());
    }

    @Test
    public void addCommentReport(){
        dao.addReport(commentDao.getComment(commentId).get(),userDao.findById(userId).get(),ReportReason.Inappropriate);
        assertEquals(2,commentDao.getComment(reviewId).get().getReports().size());
    }

}
