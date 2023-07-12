import ar.edu.itba.paw.models.*;
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
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.HashSet;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:report-dao-test.sql")
public class ReportDaoTest {
    private final Long commentId=1L;
    private final Long reviewId=3L;
    private final Long contentId=2L;
    private final Long reviewReportId=3L;
    private final Long commentReportId=3L;
    private final Long userId=2L;

    private static final int REPORTS_AMOUNT = 10;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private DataSource ds;
    @Autowired
    private ReportDao dao;
    private JdbcTemplate jdbcTemplate;

    private User USER=new User(1L,"mateoperezrivera@gmail.com","brandyhuevo","secret",0L,null,"user");
    private User USER2=new User(2L,"mateoperezrivera@gmail.com","brandyhuevo","secret",0L,null,"user");
    private Content CONTENT=new Content(2L,"adventure time",null,"A 12-year-old boy and his best friend, wise 28-year-old dog with magical powers","2010-2018","Animation","jhon lasseter","1:32",92,"serie");
    private Review REVIEW=new Review(3L,"serie","bad tv show","dont recommend it!",2,USER,CONTENT);

    private Comment COMMENT=new Comment(1L,USER2,REVIEW,"comment");
    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
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
    public void testDeleteComment(){
        dao.delete(em.find(Comment.class,1L));
        em.flush();
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "comment", "commentId = " + COMMENT.getCommentId()));    }

    @Test
    @Rollback
    public void testDeleteReview(){
        dao.delete(em.find(Review.class,3L));
        em.flush();
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "review", "reviewId = " + REVIEW.getId()));

    }
    @Test
    @Rollback
    public void testRemoveReviewReports(){
        Review review=em.find(Review.class,REVIEW.getId());
        dao.removeReports(review);
        em.flush();
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "reviewreport", "reviewId = " + REVIEW.getId()));
    }

    @Test
    @Rollback
    public void testRemoveCommentReports(){
        Comment comment=em.find(Comment.class,COMMENT.getCommentId());
        dao.removeReports(comment);
        em.flush();
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "commentreport", "commentId = " + COMMENT.getCommentId()));
    }

    @Test
    public void getReportedReviews(){
        assertEquals(1,dao.getReportedReviews(1,REPORTS_AMOUNT).getElemsAmount());
    }
    @Test
    public void getReportedComments(){
        assertEquals(1,dao.getReportedComments(1,REPORTS_AMOUNT).getElemsAmount());
    }
    @Test
    public void getReportedReviewsByReason(){
        assertEquals(1,dao.getReportedReviewsByReason(ReportReason.Other,1,REPORTS_AMOUNT).getElemsAmount());
        assertEquals(0,dao.getReportedReviewsByReason(ReportReason.Inappropriate,1,REPORTS_AMOUNT).getElemsAmount());
    }
    @Test
    public void getReportedCommentsByReason(){
        assertEquals(1,dao.getReportedCommentsByReason(ReportReason.Inappropriate,1,REPORTS_AMOUNT).getElemsAmount());
        assertEquals(0,dao.getReportedCommentsByReason(ReportReason.Other,1,REPORTS_AMOUNT).getElemsAmount());
    }

    @Test
    public void addReviewReport(){
        Review review=em.find(Review.class,REVIEW.getId());
        User user2=em.find(User.class,USER2.getId());
        dao.addReport(review,user2,ReportReason.Inappropriate);
        em.flush();
        assertEquals(2, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "reviewreport", "reviewId = " + REVIEW.getId()));
    }

    @Test
    public void addCommentReport(){
        Comment comment=em.find(Comment.class,COMMENT.getCommentId());
        User user2=em.find(User.class,USER2.getId());
        dao.addReport(comment,user2,ReportReason.Inappropriate);
        em.flush();
        assertEquals(2, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "commentreport", "commentId = " + COMMENT.getCommentId()));
    }

}
