
import ar.edu.itba.paw.models.Comment;
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
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.ArrayList;
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

    private User USER=new User(1L,"mateoperezrivera@gmail.com","brandyhuevo","secret",0L,null,"user");
    private User USER2=new User(2L,"mateoperezrivera@gmail.com","brandyhuevo","secret",0L,null,"user");

    private Content CONTENT=new Content(1L,"toy story",null,"Woody is stolen from his home by toy dealer Al McWhiggin.","1999","Animation","jhon lasseter","1:32",92,"movie");

    private Content CONTENT2=new Content(2L,"adventure time",null,"A 12-year-old boy and his best friend, wise 28-year-old dog with magical powers","2010-2018","Animation","jhon lasseter","1:32",92,"serie");
    private Review REVIEW2=new Review(3L,"serie","bad tv show","dont recommend it!",2,USER,CONTENT);

    private Review REVIEW=new Review(2L,"movie","great movie","loved it! great actors",5,USER2,CONTENT2);



    private JdbcTemplate jdbcTemplate;

    private static final int REVIEW_AMOUNT = 10;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);

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
        Content content=em.find(Content.class,CONTENT.getId());
        final List<Review> reviewList=dao.getAllReviews(content,1,REVIEW_AMOUNT).getPageContent();
        assertEquals(1, reviewList.size());
        assertEquals("great movie", reviewList.get(0).getName());
    }
    @Test
    public void testGetAllUserReviews(){
        User user=em.find(User.class,USER.getId());
        final List<Review> reviewList=dao.getAllUserReviews(user,1,REVIEW_AMOUNT).getPageContent();
        assertEquals(1, reviewList.size());
        assertEquals(2, reviewList.get(0).getId());
    }

    @Test
    @Rollback
    public void testCreate(){
        User user=em.find(User.class,USER.getId());
        Content content=em.find(Content.class,CONTENT.getId());
        final Optional<Review> maybeReview=dao.addReview("muy buena peli", "", 4, "serie", user,content);
        assertTrue(maybeReview.isPresent());
        em.flush();
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbcTemplate, "review"));

    }
    @Test
    @Rollback
    public void testDelete(){
        dao.deleteReview(REVIEW.getId());
        em.flush();
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "review"));

    }

    @Test
    @Rollback
    public void testUpdate(){
        dao.updateReview("not that good","i overestimated it",4,2L);
        em.flush();
        Review changed=em.find(Review.class,2L);
        assertTrue(changed.getName()=="not that good" && changed.getRating()==4);

    }


}