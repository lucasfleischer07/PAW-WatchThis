import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.ContentDao;
import ar.edu.itba.paw.persistance.UserDao;
import config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.test.jdbc.JdbcTestUtils;
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
@Sql(scripts = "classpath:user-dao-test.sql")
public class UserDaoTest {
    private final static User testUser=new User(1L,"mateoperezrivera@gmail.com","brandyhuevo","secret",0L,null,"user");
    private final static String PASSWORD = "password";
    private final static String NAME = "NAME";
    private final static String EMAIL = "e@mail.com";
    @Autowired
    private DataSource ds;

    @Autowired
    private UserDao dao;
    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    private static final int ELEMS_AMOUNT = 10;
    private Content CONTENT1=new Content(172L,"Tonari no Totoro",null,"A 12-year-old boy and his best friend, wise 28-year-old dog with magical powers","1988","Animation Comedy Family","Hayao Miyazaki","1 hour 26 minutes'",86,"movie");
    private Content CONTENT2=new Content(501L,"Toy Story 2",null,"A 12-year-old boy and his best friend, wise 28-year-old dog with magical powers","1999","Animation","jhon lasseter","1:32",92,"movie");
    private Content CONTENT3=new Content(10L,"Il buono, il brutto, il cattivo",null,"A 12-year-old boy and his best friend, wise 28-year-old dog with magical powers","1966","Adventure Western","Sergio Leone","2 hours 41 minutes",161,"movie");
    private Content CONTENT4=new Content(492L,"Avrupa Yakasi",null,"A 12-year-old boy and his best friend, wise 28-year-old dog with magical powers","2004–2009","Comedy","Gülse Birsel","1 hour",60,"serie");
    private User USER=new User(2L,"mateoperezrivera@gmail.com","brandyhuevo","secret",0L,null,"user");
    private User USER2=new User(3L,"mateoperezrivera@gmail.com","brandyhuevo","secret",0L,null,"user");


    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    @Rollback
    public void testCreate(){
        final Optional<User> maybeUser= dao.create(EMAIL,NAME,PASSWORD);
        assertTrue(maybeUser.isPresent());
        assertEquals(PASSWORD, maybeUser.get().getPassword());
        assertEquals(NAME, maybeUser.get().getUserName());
        assertEquals(EMAIL, maybeUser.get().getEmail());
        em.flush();
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbcTemplate, "userdata"));

    }

    @Test
    public void testFindByEmail(){
        Optional<User> maybeUser=dao.findByEmail(USER.getEmail());
        assertTrue(maybeUser.isPresent());
        assertEquals(USER.getPassword(), maybeUser.get().getPassword());
        assertEquals(USER.getUserName(), maybeUser.get().getUserName());
        assertEquals(USER.getEmail(), maybeUser.get().getEmail());
        assertEquals(USER.getId(), maybeUser.get().getId());
        assertNull(maybeUser.get().getImage());
    }
    @Test
    public void testFindByEmailError(){
        Optional<User> maybeUser=dao.findByEmail(EMAIL);
        assertFalse(maybeUser.isPresent());
    }
    @Test
    public void testFindById(){
        Optional<User> maybeUser=dao.findById(USER.getId());
        assertTrue(maybeUser.isPresent());
        assertEquals(USER.getPassword(), maybeUser.get().getPassword());
        assertEquals(USER.getUserName(), maybeUser.get().getUserName());
        assertEquals(USER.getEmail(), maybeUser.get().getEmail());
        assertEquals(USER.getId(), maybeUser.get().getId());
        assertNull(maybeUser.get().getImage());
    }
    @Test
    public void testFindByIdError(){
        Optional<User> maybeUser=dao.findById(100L);
        assertFalse(maybeUser.isPresent());
    }
    @Test
    public void testFindByUserName(){
        Optional<User> maybeUser=dao.findByUserName(USER.getUserName());
        assertTrue(maybeUser.isPresent());
        assertEquals(USER.getPassword(), maybeUser.get().getPassword());
        assertEquals(USER.getUserName(), maybeUser.get().getUserName());
        assertEquals(USER.getEmail(), maybeUser.get().getEmail());
        assertEquals(USER.getId(), maybeUser.get().getId());
        assertNull(maybeUser.get().getImage());
    }
    @Test
    public void testFindByUserNameError(){
        Optional<User> maybeUser=dao.findByUserName(NAME);
        assertFalse(maybeUser.isPresent());
    }

    @Test
    @Rollback
    public void testSetPassword(){
        User user=USER;
        dao.setPassword("newsecret",user);
        User changedUser=em.find(User.class,2L);
        assertTrue(changedUser.getPassword()=="newsecret");

    }
    @Test
    @Rollback
    public void testSetProfilePicture(){
        byte[] image= new String("image").getBytes();
        dao.setProfilePicture(image,USER);
        User changedUser=em.find(User.class,2L);
        assertArrayEquals(image, changedUser.getImage());
    }

    @Test
    public void testGetWatchList(){
        User user=em.find(User.class,2L);
        List<Content> contentList=dao.getWatchList(user,1,ELEMS_AMOUNT).getPageContent();
        assertEquals(2,contentList.size());
        assertEquals(172L,contentList.get(1).getId());

    }
    @Test
    public void testSearchContentInWatchList(){
        User user=em.find(User.class,2L);
        Optional<Long> searchResult=dao.searchContentInWatchList(user,172L);
        assertTrue(searchResult.isPresent());
        assertEquals(172L, (long) searchResult.get());
    }


    @Test
    public void testGetUserViewedList(){
        User user=em.find(User.class,2L);
        List<Content> contentList=dao.getUserViewedList(user,1,ELEMS_AMOUNT).getPageContent();
        assertEquals(2,contentList.size());
        assertEquals(501L,contentList.get(1).getId());
    }
    @Test
    public void testSearchContentInViewedList(){
        User user=em.find(User.class,2L);
        Optional<Long> searchResult=dao.searchContentInViewedList(user,501L);
        assertTrue(searchResult.isPresent());
        assertEquals(501L, (long) searchResult.get());
    }

    @Test
    @Rollback
    public void testPromoteUser(){
        dao.promoteUser(USER);
        assertEquals("admin",em.find(User.class,2L).getRole());
    }

    @Test
    @Rollback
    public void testAddToWatchList(){
        User user=em.find(User.class,2L);
        Content content=CONTENT4;
        dao.addToWatchList(user,content);
        em.flush();
        user=em.find(User.class,2L);
        assertTrue(user.getWatchlist().size()==3);
    }
    @Test
    @Rollback
    public void testDeleteFromWatchList(){
        User user=USER;
        Content content=CONTENT1;
        dao.deleteFromWatchList(user,content);
        em.flush();
        user=em.find(User.class,2L);
        assertTrue(user.getWatchlist().size()==1);

    }

    @Test
    @Rollback
    public void testAddToViewedList(){
        User user=em.find(User.class,2L);
        Content content=CONTENT4;
        dao.addToViewedList(user,content);
        user=em.find(User.class,2L);
        List<Content> contentList=user.getViewedList();
        assertEquals(3,contentList.size());
    }
    @Test
    @Rollback
    public void testDeleteFromViewedList(){
        User user=em.find(User.class,2L);
        Content content=CONTENT2;
        dao.deleteFromViewedList(user,content);
        em.flush();
        user=em.find(User.class,2L);
        assertTrue(user.getViewedList().size()==1);

    }



}