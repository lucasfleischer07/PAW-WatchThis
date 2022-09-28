import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.persistance.ContentJdbcDao;
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

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:content-dao-test.sql")
public class ContentJdbcDaoTest {

    @Autowired
    private DataSource ds;
    @Autowired
    private ContentJdbcDao dao;
    private JdbcTemplate jdbcTemplate;
    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testGetAllContent(){
        assertTrue(dao.getAllContent("ANY","ANY").size()==6);

    }


    @Test
    public void  GetAllContentSort(){
        List<Content> contentList=dao.getAllContent("ANY","A-Z");
        assertTrue(contentList.size()==6);
        assertTrue(contentList.get(0).getName().equals("Avrupa Yakasi"));
    }
    @Test
    public void  GetAllContentType(){
        List<Content> contentList=dao.getAllContent("movie","ANY");
        assertTrue(contentList.size()==4);
    }

    @Test
    public void findByNameTest(){
        Optional<Content> contentOptional=dao.findByName("Toy Story 2");
        assertTrue(contentOptional.isPresent());
        assertTrue(contentOptional.get().getId()==501);
    }

    @Test
    public void findByGenreTest(){
        List<Content> contentList=dao.findByGenre("all","Animation","ANY");
        assertTrue(contentList.size()==2);
    }

    @Test
    public void findByIdTest(){
        Optional<Content> contentOptional=dao.findById(501);
        assertTrue(contentOptional.isPresent());
        assertTrue(contentOptional.get().getName().equals("Toy Story 2"));
    }

    @Test
    public void findByTypeTest(){
        List<Content> contentList=dao.findByType("movie");
        assertTrue(contentList.size()==4);
    }

    @Rollback
    @Test
    public void addPointsTest(){
        dao.addContentPoints(501,5);
        dao.addContentPoints(501,3);
        Optional<Content> contentOptional=dao.findById(501);
        assertTrue(contentOptional.isPresent());
        assertTrue(contentOptional.get().getRating()==4);
    }

    @Rollback
    @Test
    public void decreasePointsTest(){
        dao.addContentPoints(501,5);
        dao.decreaseContentPoints(501,4);
        Optional<Content> contentOptional=dao.findById(501);
        assertTrue(contentOptional.isPresent());
        assertTrue(contentOptional.get().getRating()==1);
    }

    @Test
    public void getBestRatedTest(){
        List<Content> contentList=dao.getBestRated();
        assertTrue(contentList.size()==1);
        assertTrue(contentList.get(0).getId()==172);
    }

    @Test
    public void getLessDuration(){
        List<Content> contentList=dao.getLessDuration("movie");
        assertTrue(contentList.size()==4);
        assertTrue(contentList.get(0).getId()==172);
    }

    @Test
    public void getLastAdded(){
        List<Content> contentList=dao.getLastAdded();
        assertTrue(contentList.size()==6);
        assertTrue(contentList.get(0).getId()==501);
    }

    @Test
    @Rollback
    public void createContentTest(){
        dao.contentCreate("new","description","2022","Animation","brandyhuevo",100,"100","movie",null);
        assertTrue(dao.getAllContent("ANY","ANY").size()==7);
        assertTrue(dao.findByName("new").isPresent());
        assertTrue(dao.findByGenre("all","Animation","ANY").size()==3);
    }




}
