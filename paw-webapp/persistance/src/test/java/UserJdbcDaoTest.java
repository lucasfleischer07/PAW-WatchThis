import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.UserDao;
import ar.edu.itba.paw.persistance.UserJdbcDao;
import config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:user-dao-test.sql")
public class UserJdbcDaoTest {
    private final static User testUser=new User(1L,"mateoperezrivera@gmail.com","brandyhuevo","secret",0L,null);
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
        assertEquals(null, maybeUser.get().getImage());
    }

    @Test
    public void testFindByEmail(){
        Optional<User> maybeUser=dao.findByEmail(testUser.getEmail());
        assertTrue(maybeUser.isPresent());
        assertEquals(testUser.getPassword(), maybeUser.get().getPassword());
        assertEquals(testUser.getUserName(), maybeUser.get().getUserName());
        assertEquals(testUser.getEmail(), maybeUser.get().getEmail());
        assertEquals(testUser.getId(), maybeUser.get().getId());
        assertEquals(null, maybeUser.get().getImage());
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
        assertEquals(null, maybeUser.get().getImage());
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
        assertEquals(null, maybeUser.get().getImage());
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

    }
    @Test
    public void testSetProfilePicture(){
        byte[] image= new String("njasndasdjnian").getBytes();
        dao.setProfilePicture(image,testUser);
        Optional<User> maybeUser=dao.findByUserName(testUser.getUserName());
        assertTrue(maybeUser.isPresent());
        assertTrue(Arrays.equals(image,maybeUser.get().getImage()));
    }
}
