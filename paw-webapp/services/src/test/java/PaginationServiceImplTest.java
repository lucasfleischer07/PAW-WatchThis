import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.persistance.ContentDao;
import ar.edu.itba.paw.services.ContentServiceImpl;
import ar.edu.itba.paw.services.PaginationServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PaginationServiceImplTest {


    private PaginationServiceImpl ps = new PaginationServiceImpl() ;

    private static final List<Content> CONTENT_LIST = new ArrayList<>();

    @Test
    public void nullContentPaginatoin(){
        List<Content> paginatedContent = ps.contentPagination(null,1);
        Assert.assertNull(paginatedContent);
    }

    @Test
    public void correctContentPagination(){
        CONTENT_LIST.add(new Content(1L,"The Lord of The Rings",null,"Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.","2003","Action","Peter Jackson","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(7L,"Harry Potter",null,"Harry, Ron, and Hermione search for Voldemort's remaining Horcruxes in their effort to destroy the Dark Lord as the final battle rages on at Hogwarts.","2003","Action","David Yates","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(15L,"The Dark Knight",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(10L,"Pulp Fiction  ",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(19L,"Fight Club",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(20L,"Inception",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(31L,"The Empire strikes back",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(37L,"The Matrix",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(4L,"Psycho",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(69L,"The godfather",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(50L,"Terminator",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(34L,"Interestelar",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(81L,"Parasite",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(100L,"Gladiator",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(110L,"Walle",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(120L,"memento",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(130L,"The Shining",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(140L,"Avengers",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(150L,"Naruto",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(160L,"Avatar",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(170L,"Dragon Ball",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));


        List<Content> paginatedContent = ps.contentPagination(CONTENT_LIST,1);
        Assert.assertNotNull(paginatedContent);
        Assert.assertEquals(CONTENT_LIST.subList(0,18),paginatedContent);
    }

    @Test
    public void incompletePageContentPagination(){
        CONTENT_LIST.add(new Content(1L,"The Lord of The Rings",null,"Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.","2003","Action","Peter Jackson","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(7L,"Harry Potter",null,"Harry, Ron, and Hermione search for Voldemort's remaining Horcruxes in their effort to destroy the Dark Lord as the final battle rages on at Hogwarts.","2003","Action","David Yates","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(15L,"The Dark Knight",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(10L,"Pulp Fiction  ",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(19L,"Fight Club",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(20L,"Inception",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(31L,"The Empire strikes back",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(37L,"The Matrix",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(4L,"Psycho",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(69L,"The godfather",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(50L,"Terminator",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(34L,"Interestelar",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(81L,"Parasite",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(100L,"Gladiator",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(110L,"Walle",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(120L,"memento",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(130L,"The Shining",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(140L,"Avengers",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(150L,"Naruto",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(160L,"Avatar",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        CONTENT_LIST.add(new Content(170L,"Dragon Ball",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));

        List<Content> checkList = new ArrayList<>();
        checkList.add(new Content(150L,"Naruto",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        checkList.add(new Content(160L,"Avatar",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));
        checkList.add(new Content(170L,"Dragon Ball",null,"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.","2008","Action","Christopher Nolan","3 hours",130,"movie"));


        List<Content> paginatedContent = ps.contentPagination(CONTENT_LIST,2);
        Assert.assertNotNull(paginatedContent);
        Assert.assertEquals(CONTENT_LIST.subList(18,21),paginatedContent);
    }

    @Test
    public void testPaginationValid(){
        boolean inValid = ps.checkPagination(40,3);
        Assert.assertFalse(inValid);
    }

    @Test
    public void testPaginationInValid(){
        boolean inValid = ps.checkPagination(30,4);
        Assert.assertTrue(inValid);
    }





}
