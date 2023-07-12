import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.CommentDao;
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
    private JdbcTemplate jdbcTemplate;
    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }
    private User USER=new User(1L,"mateoperezrivera@gmail.com","brandyhuevo","secret",0L,null,"user");
    private User USER2=new User(2L,"mateoperezrivera@gmail.com","brandyhuevo","secret",0L,null,"user");
    private Content CONTENT=new Content(2L,"adventure time",null,"A 12-year-old boy and his best friend, wise 28-year-old dog with magical powers","2010-2018","Animation","jhon lasseter","1:32",92,"serie");
    private Review REVIEW=new Review(3L,"serie","bad tv show","dont recommend it!",2,USER,CONTENT);

    private Comment COMMENT=new Comment(1L,USER2,REVIEW,"comment");
    @Test
    public void getCommentTest(){
        assertTrue( dao.getComment(commentId).get().getCommentId()==commentId);
    }
    @Test
    @Rollback
    public void addCommentTest(){
        dao.addComment(REVIEW,USER,"comment");
        em.flush();
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "comment", "commentId != " + commentId));
    }
    @Test
    @Rollback
    public void deleteCommentTest(){
        dao.deleteComment(em.find(Comment.class,2L));
        em.flush();
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "comment", "commentId = " + COMMENT.getCommentId()));
    }
}
