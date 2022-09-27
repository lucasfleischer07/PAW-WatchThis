package ar.itba.edu.paw.services;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.UserDao;
import ar.edu.itba.paw.services.EmailService;
import ar.edu.itba.paw.services.EmailServiceImpl;
import ar.edu.itba.paw.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;

public class UserServiceImplTest {
    private UserServiceImpl us;
    private UserDao userDao;
    private EmailService es;
    private PasswordEncoder pe;
    private static final String EMAIL="foo@bar.com";
    private static final String PASSWORD="secret";
    private static final String USERNAME="testUser";
    private static final Long RATING=0L;


    @Test
    public void name() {
    }

    @Before
    public void setup(){
        es=new EmailServiceImpl();
        userDao= Mockito.mock(UserDao.class);
        us=new UserServiceImpl(userDao,es,pe);
    }

    @Test
    public void testCreate(){
        Mockito.when(userDao.create(eq(EMAIL),eq(USERNAME),eq(PASSWORD),eq(RATING)))
                .thenReturn(Optional.of(new User(1L,EMAIL,USERNAME, pe.encode(PASSWORD),0L,null )));
        final User user= us.register(new User(null,EMAIL,USERNAME,PASSWORD,0L,null)).get();
        assertNotNull(user);
        assertEquals(EMAIL,user.getEmail());
        assertEquals(pe.encode(PASSWORD),user.getPassword());
        assertEquals(USERNAME,user.getUserName());

    }



}
