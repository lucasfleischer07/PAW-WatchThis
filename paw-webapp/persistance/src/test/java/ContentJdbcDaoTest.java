import ar.edu.itba.paw.persistance.UserJdbcDao;
import config.TestConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
public class ContentJdbcDaoTest {

    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(classes = TestConfig.class)
    public class UserJDBCDaoTest{
        @Autowired
        private UserJdbcDao dao;

        @Test
        public void testCreate(){

        }
    }
}
