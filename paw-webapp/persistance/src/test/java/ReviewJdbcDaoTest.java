
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.persistance.ReviewDao;
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

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:review-dao-test.sql")
public class ReviewJdbcDaoTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private ReviewDao dao;

    private JdbcTemplate jdbcTemplate;
    final private Review testReview=new Review(3L,"movie",2L,"great movie","",5,"brandyhuevo","adventure time");

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testFindById(){
        final Optional<Review> maybeReview=dao.findById(1L);
        assertTrue(maybeReview.isPresent());
        assertEquals(1L, maybeReview.get().getId());
        assertEquals("great movie", maybeReview.get().getName());
        assertEquals("movie",maybeReview.get().getType());
    }

    @Test
    public void testGetAllReviews(){
        final List<Review> reviewList=dao.getAllReviews(1L);
        assertEquals(1, reviewList.size());
        assertEquals("great movie", reviewList.get(0).getName());

    }
    @Test
    public void testGetAllUserReviews(){
        final List<Review> reviewList=dao.getAllUserReviews("brandyhuevo");
        assertEquals(1, reviewList.size());
        assertEquals(1, reviewList.get(0).getId());
    }

    @Test
    @Rollback
    public void testCreate(){
        dao.addReview(testReview);
        final Optional<Review> maybeReview= dao.findById(testReview.getId());
        assertTrue(maybeReview.isPresent());
        assertEquals(testReview.getId(), maybeReview.get().getId());
        assertEquals(testReview.getName(), maybeReview.get().getName());
        assertEquals(testReview.getRating(), maybeReview.get().getRating());
        assertEquals(testReview.getContentId(),maybeReview.get().getContentId());
        assertEquals(2,dao.getAllReviews(testReview.getContentId()).size());
    }
    @Test
    @Rollback
    public void testDelete(){
        dao.deleteReview(1L);
        assertFalse(dao.findById(1L).isPresent());
        assertEquals(0, dao.getAllReviews(1L).size());
    }

    @Test
    @Rollback
    public void testUpdate(){
        dao.updateReview("not that good","i overestimated it",4,1L);
        final Optional<Review> maybeReview= dao.findById(1L);
        assertTrue(maybeReview.isPresent());
        assertEquals(4, (int) maybeReview.get().getRating());
        assertEquals("not that good", maybeReview.get().getName());
        assertEquals(1, dao.getAllReviews(1L).size());
    }

}
