import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
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
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:user-dao-test.sql")
public class UserJdbcDaoTest {
    private final static User testUser=new User(1L,"mateoperezrivera@gmail.com","brandyhuevo","secret",0L,null,"user");
    private final static String PASSWORD = "password";
    private final static String NAME = "NAME";
    private final static String EMAIL = "e@mail.com";
    @Autowired
    private DataSource ds;

    @Autowired
    private UserDao dao;

    private JdbcTemplate jdbcTemplate;


    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    @Rollback
    public void testCreate(){
        final Optional<User> maybeUser= dao.create(EMAIL,NAME,PASSWORD,0L);
        assertTrue(maybeUser.isPresent());
        assertEquals(PASSWORD, maybeUser.get().getPassword());
        assertEquals(NAME, maybeUser.get().getUserName());
        assertEquals(EMAIL, maybeUser.get().getEmail());
        assertNull(maybeUser.get().getImage());
        assertTrue(dao.findByUserName("brandyhuevo").isPresent());
    }

    @Test
    public void testFindByEmail(){
        Optional<User> maybeUser=dao.findByEmail(testUser.getEmail());
        assertTrue(maybeUser.isPresent());
        assertEquals(testUser.getPassword(), maybeUser.get().getPassword());
        assertEquals(testUser.getUserName(), maybeUser.get().getUserName());
        assertEquals(testUser.getEmail(), maybeUser.get().getEmail());
        assertEquals(testUser.getId(), maybeUser.get().getId());
        assertNull(maybeUser.get().getImage());
    }
    @Test
    public void testFindByEmailError(){
        Optional<User> maybeUser=dao.findByEmail(EMAIL);
        assertFalse(maybeUser.isPresent());
    }
    @Test
    public void testFindById(){
        Optional<User> maybeUser=dao.findById(testUser.getId());
        assertTrue(maybeUser.isPresent());
        assertEquals(testUser.getPassword(), maybeUser.get().getPassword());
        assertEquals(testUser.getUserName(), maybeUser.get().getUserName());
        assertEquals(testUser.getEmail(), maybeUser.get().getEmail());
        assertEquals(testUser.getId(), maybeUser.get().getId());
        assertNull(maybeUser.get().getImage());
    }
    @Test
    public void testFindByIdError(){
        Optional<User> maybeUser=dao.findById(100L);
        assertFalse(maybeUser.isPresent());
    }
    @Test
    public void testFindByUserName(){
        Optional<User> maybeUser=dao.findByUserName(testUser.getUserName());
        assertTrue(maybeUser.isPresent());
        assertEquals(testUser.getPassword(), maybeUser.get().getPassword());
        assertEquals(testUser.getUserName(), maybeUser.get().getUserName());
        assertEquals(testUser.getEmail(), maybeUser.get().getEmail());
        assertEquals(testUser.getId(), maybeUser.get().getId());
        assertNull(maybeUser.get().getImage());
    }
    @Test
    public void testFindByIdUserNameError(){
        Optional<User> maybeUser=dao.findByUserName(NAME);
        assertFalse(maybeUser.isPresent());
    }
    @Test
    @Rollback
    public void testSetPassword(){
        dao.setPassword("newsecret",testUser);
        Optional<User> maybeUser=dao.findByUserName(testUser.getUserName());
        assertTrue(maybeUser.isPresent());
        assertEquals("newsecret", maybeUser.get().getPassword());
        assertEquals(1, maybeUser.get().getId());
        assertEquals("brandyhuevo", maybeUser.get().getUserName());

    }
    @Test
    public void testSetProfilePicture(){
        byte[] image= new String("image").getBytes();
        dao.setProfilePicture(image,testUser);
        Optional<User> maybeUser=dao.findByUserName(testUser.getUserName());
        assertTrue(maybeUser.isPresent());
        assertArrayEquals(image, maybeUser.get().getImage());
    }

    @Test
    public void testGetWatchList(){
        User user=dao.findByUserName("brandyhuevo").get();
        List<Content> contentList=dao.getWatchList(user);
        assertEquals(2,contentList.size());
        assertEquals(172L,contentList.get(1).getId());

    }
    @Test
    public void testSearchContentInWatchList(){
        User user=dao.findByUserName("brandyhuevo").get();
        Optional<Long> searchResult=dao.searchContentInWatchList(user,172L);
        assertTrue(searchResult.isPresent());
        assertEquals(172L, (long) searchResult.get());
    }
    @Test
    public void testGetUserWatchListContent(){
        User user=dao.findByUserName("brandyhuevo").get();
        List<Long> idList=dao.getUserWatchListContent(user);
        assertEquals(2, idList.size());
        assertTrue(idList.contains(172L));
        assertTrue(idList.contains(10L));
    }

    @Test
    public void testGetUserViewedList(){
        User user=dao.findByUserName("brandyhuevo").get();
        List<Content> contentList=dao.getUserViewedList(user);
        assertEquals(2,contentList.size());
        assertEquals(501L,contentList.get(1).getId());
    }
    @Test
    public void testSearchContentInViewedList(){
        User user=dao.findByUserName("brandyhuevo").get();
        Optional<Long> searchResult=dao.searchContentInViewedList(user,501L);
        assertTrue(searchResult.isPresent());
        assertEquals(501L, (long) searchResult.get());
    }
    @Test
    public void testGetUserViewedListContent(){
        User user=dao.findByUserName("brandyhuevo").get();
        List<Long> idList=dao.getUserViewedListContent(user);
        assertEquals(2, idList.size());
        assertTrue(idList.contains(501L));
        assertTrue(idList.contains(10L));
    }
//    @Test
//    @Rollback
//    public void testPromoteUser(){
//        dao.promoteUser(1L);
//        assertEquals("admin",dao.findById(1).get().getRole());
//    }

    @Test
    @Rollback
    public void testAddToWatchList(){
        User user=dao.findByUserName("brandyhuevo").get();
        dao.addToWatchList(user,492L);
        List<Long> idList=dao.getUserWatchListContent(user);
        assertEquals(3,idList.size());
        assertTrue(idList.contains(492L));
    }
    @Test
    @Rollback
    public void testDeleteFromWatchList(){
        User user=dao.findByUserName("brandyhuevo").get();
        dao.deleteFromWatchList(user,172L);
        List<Long> idList=dao.getUserWatchListContent(user);
        assertEquals(1,idList.size());
        assertFalse(idList.contains(172L));
        assertTrue(idList.contains(10L));

    }

    @Test
    @Rollback
    public void testAddToViewedList(){
        User user=dao.findByUserName("brandyhuevo").get();
        dao.addToViewedList(user,492L);
        List<Long> idList=dao.getUserViewedListContent(user);
        assertEquals(3,idList.size());
        assertTrue(idList.contains(492L));
    }
    @Test
    @Rollback
    public void testDeleteFromViewedList(){
        User user=dao.findByUserName("brandyhuevo").get();
        dao.deleteFromViewedList(user,501L);
        List<Long> idList=dao.getUserViewedListContent(user);
        assertEquals(1,idList.size());
        assertFalse(idList.contains(501L));
        assertTrue(idList.contains(10L));
    }



}