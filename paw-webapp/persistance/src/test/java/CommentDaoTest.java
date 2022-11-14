import ar.edu.itba.paw.persistance.CommentDao;
import ar.edu.itba.paw.persistance.ReviewDao;
import ar.edu.itba.paw.persistance.UserDao;
import config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:comment-dao-test.sql")
public class CommentDaoTest {
    private final long commentId=2L;
    private final long reviewId=3L;
    private final long userId=2L;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private DataSource ds;
    @Autowired
    private CommentDao dao;
    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private UserDao userDao;
    @Test
    public void getCommentTest(){
        assertTrue( dao.getComment(commentId).isPresent());
    }
    @Test
    @Rollback
    public void addCommentTest(){
        dao.addComment(reviewDao.findById(reviewId).get(),userDao.findById(userId).get(),"comment");
        em.flush();
        assertEquals(2,reviewDao.findById(reviewId).get().getComments().size());
    }
    @Test
    @Rollback
    public void deleteCommentTest(){
        dao.deleteComment(dao.getComment(commentId).get());
        em.flush();
        assertEquals(0,reviewDao.findById(reviewId).get().getComments().size());
    }
}
