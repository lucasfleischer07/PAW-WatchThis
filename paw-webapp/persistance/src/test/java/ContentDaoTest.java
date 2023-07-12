import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Sorting;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:content-dao-test.sql")
public class ContentDaoTest {

    private static final int CONTENT_AMOUNT = 10;

    @Autowired
    private DataSource ds;
    @Autowired
    private ContentDao dao;
    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;
    private User USER=new User(1L,"mateoperezrivera@gmail.com","brandyhuevo","secret",0L,null,"user");
    private User USER2=new User(2L,"mateoperezrivera@gmail.com","brandyhuevo","secret",0L,null,"user");
    private Content CONTENT1=new Content(172L,"Tonari no Totoro",null,"A 12-year-old boy and his best friend, wise 28-year-old dog with magical powers","1988","Animation Comedy Family","Hayao Miyazaki","1 hour 26 minutes'",86,"movie");
    private Content CONTENT2=new Content(501L,"Toy Story 2",null,"A 12-year-old boy and his best friend, wise 28-year-old dog with magical powers","1999","Animation","jhon lasseter","1:32",92,"movie");
    private Content CONTENT3=new Content(10L,"Il buono, il brutto, il cattivo",null,"A 12-year-old boy and his best friend, wise 28-year-old dog with magical powers","1966","Adventure Western","Sergio Leone","2 hours 41 minutes",161,"movie");
    private Content CONTENT4=new Content(492L,"Avrupa Yakasi",null,"A 12-year-old boy and his best friend, wise 28-year-old dog with magical powers","2004–2009","Comedy","Gülse Birsel","1 hour",60,"serie");
    private Content CONTENT5=new Content(497L,"Queer Eye",null,"A 12-year-old boy and his best friend, wise 28-year-old dog with magical powers","2018–","Reality-T V","David Collins","45 minutes",45,"serie");
    private Content CONTENT6=new Content(2L,"The Shawshank Redemption",null,"A 12-year-old boy and his best friend, wise 28-year-old dog with magical powers","1994","Drama","Frank Darabont","2 hours 22 minutes",142,"movie");
    private Content[] array={CONTENT1,CONTENT2,CONTENT3,CONTENT4,CONTENT5,CONTENT6};
    private List<Content> CONTENT_LIST=new ArrayList<>(Arrays.asList(array));

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testGetAllContent(){
        assertEquals(6, dao.getAllContent("ANY", null,1,CONTENT_AMOUNT).getElemsAmount());

    }

    @Test
    public void  testGetAllContentSort(){
        List<Content> contentList=dao.getAllContent("ANY", Sorting.NameAsc,1,CONTENT_AMOUNT).getPageContent();
        assertEquals(6, contentList.size());
        assertEquals("Avrupa Yakasi", contentList.get(0).getName());
    }
    @Test
    public void  testGetAllContentType(){
        List<Content> contentList=dao.getAllContent("movie",null,1,CONTENT_AMOUNT).getPageContent();
        assertEquals(4, contentList.size());
    }

    @Test
    public void testFindByNameTest(){
        Optional<Content> contentOptional=dao.findByName("Toy Story 2");
        assertTrue(contentOptional.isPresent());
        assertEquals(501, CONTENT2.getId());
    }

    @Test
    public void testFindByDuration(){
        List<Content> contentList=dao.findByDuration("movie",0,100,null,1,CONTENT_AMOUNT).getPageContent();
        assertEquals(2,contentList.size());
    }

    @Test
    public void testGetSearchedContentByGenre(){
        List<Content> contentList=dao.getSearchedContentByGenre("movie"," '%'|| " + "'Animation'" + " ||'%'",null,"toy",1,CONTENT_AMOUNT).getPageContent();
        assertEquals(1,contentList.size());

    }
    @Test
    public void testGetSearchedContentByDuration(){
        List<Content> contentList=dao.getSearchedContentByDuration("movie",80,100,null,"toy",1,CONTENT_AMOUNT).getPageContent();
        assertEquals(1,contentList.size());

    }
    @Test
    public void testGetSearchedContentByDurationAndGenre(){
        List<Content> contentList=dao.getSearchedContentByDurationAndGenre("movie"," '%'|| " + "'Animation'" + " ||'%'",90,100,null,"",1,CONTENT_AMOUNT).getPageContent();
        assertEquals(1,contentList.size());

    }

    @Test
    public void testFindByDurationAndGenre(){
        List<Content> contentList=dao.findByDurationAndGenre("movie"," '%'|| " + "'Animation'" + " ||'%'",90,1000,null,1,CONTENT_AMOUNT).getPageContent();
        assertEquals(1,contentList.size());
    }

    @Test
    public void testFindByGenreTest(){
        List<Content> contentList=dao.findByGenre("all"," '%'|| " + "'Animation'" + " ||'%'",null,1,CONTENT_AMOUNT).getPageContent();
        assertEquals(2, contentList.size());
    }

    @Test
    public void testFindByIdTest(){
        Optional<Content> contentOptional=dao.findById(501);
        assertTrue(contentOptional.isPresent());
        assertEquals("Toy Story 2", contentOptional.get().getName());
    }

    @Test
    public void testFindByTypeTest(){
        List<Content> contentList=dao.findByType("movie",1,CONTENT_AMOUNT).getPageContent();
        assertEquals(4, contentList.size());
    }

    @Test
    public void testGetBestRated(){
        List<Content> contentList=dao.getBestRated();
        assertEquals(1, contentList.size());
        assertEquals(501, contentList.get(0).getId());
    }

    @Test
    public void testGetUserRecommended(){
        User user= new User(1L,"brandyhuevo","mateoperezrivera@gmail.com","secret",0L,null ,"user");
        List<Content> contentList=dao.getUserRecommended(user);
        assertEquals(1,contentList.size());
        assertEquals(2,contentList.get(0).getId());
    }


    @Test
    public void testGetLastAdded(){
        List<Content> contentList=dao.getLastAdded();
        assertEquals(6, contentList.size());
        assertEquals(501, contentList.get(0).getId());
    }

    @Test
    public void testGetMostUserSaved(){
        List<Content> contentList=dao.getMostUserSaved();
        assertEquals(3,contentList.size());
    }

    @Test
    @Rollback
    public void testCreateContent(){
        dao.contentCreate("new","description","2022","Animation","brandyhuevo",100,"100","movie",null);
        em.flush();
        assertEquals(CONTENT_LIST.size()+1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "content"));
    }

    @Test
    @Rollback
    public void testUpdateContent(){
        String preUpdateName=CONTENT6.getName();
        dao.updateContent(CONTENT6.getId(),"name change","description","1982","Animation","me",90,"90 minutes","movie");
        Content postUpdate=em.find(Content.class,CONTENT6.getId());
        assertFalse(postUpdate.getName().equals(preUpdateName));
    }

    @Test
    @Rollback
    public void testUpdateContentWithImage(){

        String preUpdateName=CONTENT6.getName();
        byte[] preUpdateImage= CONTENT6.getImage();
        dao.updateWithImageContent(CONTENT6.getId(),"name change","description","1982","Animation","me",90,"90 minutes","movie","image".getBytes());
        Content postUpdate=em.find(Content.class,CONTENT6.getId());

        assertNotEquals(postUpdate.getName(), preUpdateName);
        assertFalse(Arrays.equals(postUpdate.getImage(), preUpdateImage));
    }

    @Test
    @Rollback
    public void testDeleteContent(){
        dao.deleteContent(CONTENT6.getId());
        em.flush();
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "content", "id = " + CONTENT6.getId()));
    }


}
