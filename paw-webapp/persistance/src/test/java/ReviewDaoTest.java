
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.ContentDao;
import ar.edu.itba.paw.persistance.ReviewDao;
import ar.edu.itba.paw.persistance.UserDao;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:review-dao-test.sql")
public class ReviewDaoTest {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private DataSource ds;

    @Autowired
    private ReviewDao dao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ContentDao contentDao;


    private JdbcTemplate jdbcTemplate;

    private static final int REVIEW_AMOUNT = 10;
    private Content testContent;

    private User testUser;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
        testUser=userDao.findById(1L).get();
        testContent=contentDao.findById(2L).get();
    }

    @Test
    public void testFindById(){
        final Optional<Review> maybeReview=dao.findById(2L);
        assertTrue(maybeReview.isPresent());
        assertEquals(2L, maybeReview.get().getId());
        assertEquals("great movie", maybeReview.get().getName());
        assertEquals("movie",maybeReview.get().getType());
    }

    @Test
    public void testGetAllReviews(){
        Content content=contentDao.findById(1L).get();
        final List<Review> reviewList=dao.getAllReviews(content,1,REVIEW_AMOUNT).getPageContent();
        assertEquals(1, reviewList.size());
        assertEquals("great movie", reviewList.get(0).getName());
    }
    @Test
    public void testGetAllUserReviews(){
        final List<Review> reviewList=dao.getAllUserReviews(testUser,1,REVIEW_AMOUNT).getPageContent();
        assertEquals(1, reviewList.size());
        assertEquals(2, reviewList.get(0).getId());
    }

    @Test
    @Rollback
    public void testCreate(){
        assertEquals(1,dao.getAllReviews(contentDao.findById(2L).get(),1,REVIEW_AMOUNT ).getElemsAmount());
        User user=userDao.findByUserName("brandyhuevo").get();
        Content content=contentDao.findById(2L).get();
        final Optional<Review> maybeReview=dao.addReview("muy buena peli", "", 4, "serie", user,content);
        assertTrue(maybeReview.isPresent());
        assertEquals("muy buena peli", maybeReview.get().getName());
        assertEquals(contentDao.findById(2L).get(),maybeReview.get().getContent());
        assertEquals(2,dao.getAllUserReviews(userDao.findByUserName("brandyhuevo").get(),1,REVIEW_AMOUNT).getElemsAmount());
        assertEquals(2,dao.getAllReviews(contentDao.findById(2L).get(),1,REVIEW_AMOUNT).getElemsAmount());
    }
    @Test
    @Rollback
    public void testDelete(){
        Review review=dao.findById(2L).get();
        User user=review.getUser();
        dao.deleteReview(2L);
        em.flush();
        assertFalse(dao.findById(2L).isPresent());
        Content content=contentDao.findById(1L).get();
        assertEquals(0,content.getContentReviews().size());
        assertEquals(0,user.getUserReviews().size());
    }

    @Test
    @Rollback
    public void testUpdate(){
        dao.updateReview("not that good","i overestimated it",4,2L);
        final Optional<Review> maybeReview= dao.findById(2L);
        assertTrue(maybeReview.isPresent());
        assertEquals(4, (int) maybeReview.get().getRating());
        assertEquals("not that good", maybeReview.get().getName());
        assertEquals(1, dao.getAllReviews(contentDao.findById(1L).get(),1,REVIEW_AMOUNT).getElemsAmount());
        assertEquals("not that good", maybeReview.get().getUser().getUserReviews().get(0).getName());
    }


}
