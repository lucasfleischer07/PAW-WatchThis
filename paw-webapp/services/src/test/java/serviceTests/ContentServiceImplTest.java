//package serviceTests;
//
//import ar.edu.itba.paw.models.Content;
//import ar.edu.itba.paw.models.Sorting;
//import ar.edu.itba.paw.persistance.ContentDao;
//import ar.edu.itba.paw.services.ContentService;
//import ar.edu.itba.paw.services.ContentServiceImpl;
//import ar.edu.itba.paw.services.UserServiceImpl;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ContentServiceImplTest {
//
//    private static final String TYPE = "movie";
//    private static final String GENRE = "'%'|| 'Action' ||'%'";
//    private static final Sorting SORT = Sorting.NewestReleased;
//    private static final String DURATION_FROM = "120";
//    private static final String DURATION_TO = "150";
//    private static final String QUERY = "The Lord";
//
//
//    @InjectMocks
//    private ContentServiceImpl cs;
//
//    @Mock
//    private ContentDao mockDao;
//
//    @Test
//    public void getMasterContentTest(){
//        List<Content> responseContent=new ArrayList<>();
//        responseContent.add(new Content(1L,"The Lord of The Rings",null,"Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.","2003","Action","Peter Jackson","3 hours",130,"movie"));
//        responseContent.add(new Content(4L,"Inception",null,"A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.","2010","Action","Christopher Nolan","2 hours 28 minutes",140,"movie"));
//        Mockito.when(mockDao.getAllContent(Mockito.eq(TYPE),Mockito.eq(SORT))).thenReturn(responseContent);
//
//        List<Content> contentList = cs.getMasterContent(TYPE,null,"ANY","ANY",SORT,"ANY");
//
//        Assert.assertNotNull(contentList);
//        Assert.assertEquals(responseContent,contentList);
//    }
//
//
//    @Test
//    public void getMasterContentByGenreTest(){
//        List<String> GENRES = new ArrayList<>();
//        GENRES.add("Action");
//        List<Content> responseContent=new ArrayList<>();
//        responseContent.add(new Content(1L,"The Lord of The Rings",null,"Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.","2003","Action","Peter Jackson","3 hours",130,"movie"));
//        responseContent.add(new Content(4L,"Inception",null,"A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.","2010","Action","Christopher Nolan","2 hours 28 minutes",140,"movie"));
//        Mockito.when(mockDao.findByGenre(Mockito.eq(TYPE),Mockito.eq(GENRE),Mockito.eq(SORT))).thenReturn(responseContent);
//
//        List<Content> contentList = cs.getMasterContent(TYPE,GENRES,"ANY","ANY",SORT,"ANY");
//
//        Assert.assertNotNull(contentList);
//        Assert.assertEquals(responseContent,contentList);
//    }
//
//    @Test
//    public void getMasterContentByDurationTest(){
//        List<Content> responseContent=new ArrayList<>();
//        responseContent.add(new Content(1L,"The Lord of The Rings",null,"Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.","2003","Action","Peter Jackson","3 hours",180,"movie"));
//        responseContent.add(new Content(4L,"Inception",null,"A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.","2010","Action","Christopher Nolan","2 hours 28 minutes",160,"movie"));
//        Mockito.when(mockDao.findByDuration(Mockito.eq(TYPE),Mockito.eq(Integer.parseInt(DURATION_FROM)),Mockito.eq(Integer.parseInt(DURATION_TO)),Mockito.eq(SORT))).thenReturn(responseContent);
//
//        List<Content> contentList = cs.getMasterContent(TYPE,null,DURATION_FROM,DURATION_TO,SORT,"ANY");
//
//        Assert.assertNotNull(contentList);
//        Assert.assertEquals(responseContent,contentList);
//    }
//
//    @Test
//    public void getMasterContentSearchedContentTest(){
//        List<Content> responseContent=new ArrayList<>();
//        responseContent.add(new Content(1L,"The Lord of The Rings",null,"Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.","2003","Action","Peter Jackson","3 hours",180,"movie"));
//        Mockito.when(mockDao.getSearchedContent(Mockito.eq(TYPE),Mockito.eq(QUERY))).thenReturn(responseContent);
//
//        List<Content> contentList = cs.getMasterContent(TYPE,null,"ANY","ANY",null,QUERY);
//
//        Assert.assertNotNull(contentList);
//        Assert.assertEquals(responseContent,contentList);
//    }
//
//    @Test
//    public void getMasterContentSearchedContentByGenreTest(){
//        List<String> GENRES = new ArrayList<>();
//        GENRES.add("Action");
//        List<Content> responseContent=new ArrayList<>();
//        responseContent.add(new Content(1L,"The Lord of The Rings",null,"Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.","2003","Action","Peter Jackson","3 hours",180,"movie"));
//        responseContent.add(new Content(1L,"The Lord of The Rings 2",null,"While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.","2002","Action","Peter Jackson","3 hours",180,"movie"));
//        Mockito.when(mockDao.getSearchedContentByGenre(Mockito.eq(TYPE),Mockito.eq(GENRE),Mockito.eq(SORT),Mockito.eq(QUERY))).thenReturn(responseContent);
//
//        List<Content> contentList = cs.getMasterContent(TYPE,GENRES,"ANY","ANY",SORT,QUERY);
//
//        Assert.assertNotNull(contentList);
//        Assert.assertEquals(responseContent,contentList);
//    }
//
//    @Test
//    public void getMasterContentSearchedContentByDurationTest(){
//        List<Content> responseContent=new ArrayList<>();
//        responseContent.add(new Content(1L,"The Lord of The Rings",null,"Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.","2003","Action","Peter Jackson","3 hours",180,"movie"));
//        responseContent.add(new Content(1L,"The Lord of The Rings 2",null,"While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.","2002","Action","Peter Jackson","3 hours",180,"movie"));
//        Mockito.when(mockDao.getSearchedContentByDuration(Mockito.eq(TYPE),Mockito.eq(Integer.parseInt(DURATION_FROM)),Mockito.eq(Integer.parseInt(DURATION_TO)),Mockito.eq(SORT),Mockito.eq(QUERY))).thenReturn(responseContent);
//
//        List<Content> contentList = cs.getMasterContent(TYPE,null,DURATION_FROM,DURATION_TO,SORT,QUERY);
//
//        Assert.assertNotNull(contentList);
//        Assert.assertEquals(responseContent,contentList);
//    }
//
//    @Test
//    public void getMasterContentSearchedContentByGenreAndDuratioNTest(){
//        List<String> GENRES = new ArrayList<>();
//        GENRES.add("Action");
//        List<Content> responseContent=new ArrayList<>();
//        responseContent.add(new Content(1L,"The Lord of The Rings",null,"Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.","2003","Action","Peter Jackson","3 hours",180,"movie"));
//        responseContent.add(new Content(1L,"The Lord of The Rings 2",null,"While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.","2002","Action","Peter Jackson","3 hours",180,"movie"));
//        Mockito.when(mockDao.getSearchedContentByDurationAndGenre(Mockito.eq(TYPE),Mockito.eq(GENRE),Mockito.eq(Integer.parseInt(DURATION_FROM)),Mockito.eq(Integer.parseInt(DURATION_TO)),Mockito.eq(SORT),Mockito.eq(QUERY))).thenReturn(responseContent);
//
//        List<Content> contentList = cs.getMasterContent(TYPE,GENRES,DURATION_FROM,DURATION_TO,SORT,QUERY);
//
//        Assert.assertNotNull(contentList);
//        Assert.assertEquals(responseContent,contentList);
//    }
//
//    @Test
//    public void getGenreStringNullTest(){
//        String genreString = cs.getGenreString(null);
//        Assert.assertNotNull(genreString);
//        Assert.assertEquals("ANY",genreString);
//    }
//
//    @Test
//    public void getGenreStringMultiGenreTest(){
//        List<String> genreList = new ArrayList<>();
//        genreList.add("Action");
//        genreList.add("Sci-Fi");
//        genreList.add("Comedy");
//        String genreString = cs.getGenreString(genreList);
//        Assert.assertNotNull(genreString);
//        Assert.assertEquals("Action,Sci-Fi,Comedy",genreString);
//    }
//
//
//
//}
