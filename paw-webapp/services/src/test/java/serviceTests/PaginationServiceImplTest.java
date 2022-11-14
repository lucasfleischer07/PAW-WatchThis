package serviceTests;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
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


    private static final int CONTENT_AMOUNT = 18;
    private static final int REVIEW_AMOUNT = 3;
    private static final User REVIEW_CREATOR = new User("Juan","juan@gmail.com","12345678");
    private static final Content REVIEW_CONTENT = new Content(1L,"The Lord of The Rings",null,"Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.","2003","Action","Peter Jackson","3 hours",130,"movie");


    @Test
    public void nullContentPaginationTest(){
        List<Content> paginatedContent = ps.pagePagination(null,1,CONTENT_AMOUNT);
        Assert.assertNull(paginatedContent);
    }

    @Test
    public void correctPageTypePaginationTest(){
        List<Content> CONTENT_LIST = new ArrayList<>();
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


        List<Content> paginatedContent =  ps.pagePagination(CONTENT_LIST,1,CONTENT_AMOUNT);
        Assert.assertNotNull(paginatedContent);
        Assert.assertEquals(CONTENT_LIST.subList(0,18),paginatedContent);
        Assert.assertEquals(18,paginatedContent.size());
    }

    @Test
    public void incompletePageTypePaginationTest(){
        List<Content> CONTENT_LIST = new ArrayList<>();
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

        List<Content> incompletePaginatedContent = ps.pagePagination(CONTENT_LIST,2,CONTENT_AMOUNT);
        Assert.assertNotNull(incompletePaginatedContent);
        Assert.assertEquals(CONTENT_LIST.subList(18,21),incompletePaginatedContent);
        Assert.assertEquals(3,incompletePaginatedContent.size());
    }

    @Test
    public void pastPageTypePaginationTest(){
        List<Content> CONTENT_LIST = new ArrayList<>();
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


        List<Content> paginatedContent = ps.pagePagination(CONTENT_LIST,3,CONTENT_AMOUNT);
        Assert.assertNull(paginatedContent);

    }

    @Test
    public void correctInfiniteTypePaginationTest(){
        List<Review> REVIEW_LIST = new ArrayList<>();
        REVIEW_LIST.add(new Review(1L,"movie","Incredible movie","This isn't just a beautifully crafted gangster film. Or an outstanding family portrait, for that matter. An amazing period piece. A character study. A lesson in filmmaking and an inspiration to generations of actors, directors, screenwriters and producers. For me, this is more: this is the definitive film. 10 stars out of 10.",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(6L,"serie","Horrible movie","Up until today, I haven't bothered to review \"The Godfather\". After all, everyone pretty much knows it's one of the greatest films ever made. It's #2 on IMDb's Top 100. It won the Best Picture Oscar. And, there are nearly 1600 reviews on IMDb. So what's one more review?! Well, after completing 14,000 reviews (because I am nuts), I guess it's time I got around to reviewing a film I should have reviewed a long time ago. ",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(14L,"movie","Worst time spent in my life","Unmarketable upon box office release, Darabount's masterpiece tanked and seemed destined to obscurity. Only after the video release, did 'Shawshawk' reap the praise it so richly deserved. Is it one of the greatest films ever made? Without a doubt, yes.",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(12L,"movie","Perfect, down to the last minute detail","Exceleten pelicula, muy buenos actores",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(18L,"movie","Estoy fascinado","Maravillosa cinematografia y nivel de produccion",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(3L,"movie","Nailed it!","To my surprise they absolutely nailed it. Cumberbatch is a fantastic choice for Sherlock Holmes-he is physically right (he fits the traditional reading of the character) and he is a damn good actor. Martin Freeman, about whom I wasn't sure at first, is an excellent foil for Holmes without being the dumb sidekick that Dr Watson has often been. I thought that this series would not work, particularly after Robert Downey's interesting take on Conan Doyle's characterisation. I have been proved so wrong-it moved along at a good pace and held the attention brilliantly. My wife started by saying she didn't like it but by the end of the episode she was as enthralled as I. We are both looking forward to the rest of the series, if it is as entertaining as the first story. I was disappointed to read some reviews here that did not love it. Methinks they are too jaded to enjoy anything.",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(9L,"movie","Was really surprised by how good it was","Despite not being an auto racing fan, the stories of the rivalry between James Hunt and Niki Lauda and Lauda's accident are well known and on research was big news in the 70s. 'Rush' did seem intriguing, Ron Howard has done some good work in the past and my sister and her boyfriend absolutely raving about it. However having no knowledge of auto racing and having never found it my cup of tea there was a touch of intrepidation. As well as the worry as to whether there was going to be any bias and whether it was going to stray from the facts. After watching 'Rush', this viewer is so glad that she gave it a chance because it was a gripping and entertaining film from start to finish, and quite easily Howard's best work in some time",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(21L,"movie","In my opinion, no film has touched me more than this one","Quite simply, the greatest film ever made. Humour, sadness, action, drama and a Vietnam film all rolled into one. I'm not a stone cold, heartless villain, but it takes a lot to make me cry when I watch a movie. Bambi's mother, I couldn't care less. Jimmy Stewart in, \"Oh, what a wonderful life,\" - yeah right! The Lion King, when Mufasa bites the big one - on the verge. But seriously - I bawled my big brown eyes out, on several occasions in this film. A real tear-jerker, and a wonderful character, played to perfection by Tom Hanks. Every bit as worthy for the Oscar as Rooney was to win the Premiership in 2007. I cannot say it enough: This is THE film of all time. Watch it, and you'll see.",4,REVIEW_CREATOR,REVIEW_CONTENT));

        List<Review> paginatedContent =  ps.infiniteScrollPagination(REVIEW_LIST,1,REVIEW_AMOUNT);
        Assert.assertNotNull(paginatedContent);
        Assert.assertEquals(REVIEW_LIST.subList(0,3),paginatedContent);
        Assert.assertEquals(3,paginatedContent.size());


        paginatedContent =  ps.infiniteScrollPagination(REVIEW_LIST,2,REVIEW_AMOUNT);
        Assert.assertNotNull(paginatedContent);
        Assert.assertEquals(REVIEW_LIST.subList(0,6),paginatedContent);
        Assert.assertEquals(6,paginatedContent.size());

    }

    @Test
    public void incompleteInfiniteTypePaginationTest(){
        List<Review> REVIEW_LIST = new ArrayList<>();
        REVIEW_LIST.add(new Review(1L,"movie","Incredible movie","This isn't just a beautifully crafted gangster film. Or an outstanding family portrait, for that matter. An amazing period piece. A character study. A lesson in filmmaking and an inspiration to generations of actors, directors, screenwriters and producers. For me, this is more: this is the definitive film. 10 stars out of 10.",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(6L,"serie","Horrible movie","Up until today, I haven't bothered to review \"The Godfather\". After all, everyone pretty much knows it's one of the greatest films ever made. It's #2 on IMDb's Top 100. It won the Best Picture Oscar. And, there are nearly 1600 reviews on IMDb. So what's one more review?! Well, after completing 14,000 reviews (because I am nuts), I guess it's time I got around to reviewing a film I should have reviewed a long time ago. ",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(14L,"movie","Worst time spent in my life","Unmarketable upon box office release, Darabount's masterpiece tanked and seemed destined to obscurity. Only after the video release, did 'Shawshawk' reap the praise it so richly deserved. Is it one of the greatest films ever made? Without a doubt, yes.",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(12L,"movie","Perfect, down to the last minute detail","Exceleten pelicula, muy buenos actores",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(18L,"movie","Estoy fascinado","Maravillosa cinematografia y nivel de produccion",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(3L,"movie","Nailed it!","To my surprise they absolutely nailed it. Cumberbatch is a fantastic choice for Sherlock Holmes-he is physically right (he fits the traditional reading of the character) and he is a damn good actor. Martin Freeman, about whom I wasn't sure at first, is an excellent foil for Holmes without being the dumb sidekick that Dr Watson has often been. I thought that this series would not work, particularly after Robert Downey's interesting take on Conan Doyle's characterisation. I have been proved so wrong-it moved along at a good pace and held the attention brilliantly. My wife started by saying she didn't like it but by the end of the episode she was as enthralled as I. We are both looking forward to the rest of the series, if it is as entertaining as the first story. I was disappointed to read some reviews here that did not love it. Methinks they are too jaded to enjoy anything.",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(9L,"movie","Was really surprised by how good it was","Despite not being an auto racing fan, the stories of the rivalry between James Hunt and Niki Lauda and Lauda's accident are well known and on research was big news in the 70s. 'Rush' did seem intriguing, Ron Howard has done some good work in the past and my sister and her boyfriend absolutely raving about it. However having no knowledge of auto racing and having never found it my cup of tea there was a touch of intrepidation. As well as the worry as to whether there was going to be any bias and whether it was going to stray from the facts. After watching 'Rush', this viewer is so glad that she gave it a chance because it was a gripping and entertaining film from start to finish, and quite easily Howard's best work in some time",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(21L,"movie","In my opinion, no film has touched me more than this one","Quite simply, the greatest film ever made. Humour, sadness, action, drama and a Vietnam film all rolled into one. I'm not a stone cold, heartless villain, but it takes a lot to make me cry when I watch a movie. Bambi's mother, I couldn't care less. Jimmy Stewart in, \"Oh, what a wonderful life,\" - yeah right! The Lion King, when Mufasa bites the big one - on the verge. But seriously - I bawled my big brown eyes out, on several occasions in this film. A real tear-jerker, and a wonderful character, played to perfection by Tom Hanks. Every bit as worthy for the Oscar as Rooney was to win the Premiership in 2007. I cannot say it enough: This is THE film of all time. Watch it, and you'll see.",4,REVIEW_CREATOR,REVIEW_CONTENT));

        List<Review> paginatedContent =  ps.infiniteScrollPagination(REVIEW_LIST,3,REVIEW_AMOUNT);
        Assert.assertNotNull(paginatedContent);
        Assert.assertEquals(REVIEW_LIST.subList(0,8),paginatedContent);
        Assert.assertEquals(8,paginatedContent.size());
    }

    @Test
    public void PagePastInfiniteTypePaginationTest(){
        List<Review> REVIEW_LIST = new ArrayList<>();
        REVIEW_LIST.add(new Review(1L,"movie","Incredible movie","This isn't just a beautifully crafted gangster film. Or an outstanding family portrait, for that matter. An amazing period piece. A character study. A lesson in filmmaking and an inspiration to generations of actors, directors, screenwriters and producers. For me, this is more: this is the definitive film. 10 stars out of 10.",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(6L,"serie","Horrible movie","Up until today, I haven't bothered to review \"The Godfather\". After all, everyone pretty much knows it's one of the greatest films ever made. It's #2 on IMDb's Top 100. It won the Best Picture Oscar. And, there are nearly 1600 reviews on IMDb. So what's one more review?! Well, after completing 14,000 reviews (because I am nuts), I guess it's time I got around to reviewing a film I should have reviewed a long time ago. ",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(14L,"movie","Worst time spent in my life","Unmarketable upon box office release, Darabount's masterpiece tanked and seemed destined to obscurity. Only after the video release, did 'Shawshawk' reap the praise it so richly deserved. Is it one of the greatest films ever made? Without a doubt, yes.",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(12L,"movie","Perfect, down to the last minute detail","Exceleten pelicula, muy buenos actores",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(18L,"movie","Estoy fascinado","Maravillosa cinematografia y nivel de produccion",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(3L,"movie","Nailed it!","To my surprise they absolutely nailed it. Cumberbatch is a fantastic choice for Sherlock Holmes-he is physically right (he fits the traditional reading of the character) and he is a damn good actor. Martin Freeman, about whom I wasn't sure at first, is an excellent foil for Holmes without being the dumb sidekick that Dr Watson has often been. I thought that this series would not work, particularly after Robert Downey's interesting take on Conan Doyle's characterisation. I have been proved so wrong-it moved along at a good pace and held the attention brilliantly. My wife started by saying she didn't like it but by the end of the episode she was as enthralled as I. We are both looking forward to the rest of the series, if it is as entertaining as the first story. I was disappointed to read some reviews here that did not love it. Methinks they are too jaded to enjoy anything.",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(9L,"movie","Was really surprised by how good it was","Despite not being an auto racing fan, the stories of the rivalry between James Hunt and Niki Lauda and Lauda's accident are well known and on research was big news in the 70s. 'Rush' did seem intriguing, Ron Howard has done some good work in the past and my sister and her boyfriend absolutely raving about it. However having no knowledge of auto racing and having never found it my cup of tea there was a touch of intrepidation. As well as the worry as to whether there was going to be any bias and whether it was going to stray from the facts. After watching 'Rush', this viewer is so glad that she gave it a chance because it was a gripping and entertaining film from start to finish, and quite easily Howard's best work in some time",4,REVIEW_CREATOR,REVIEW_CONTENT));
        REVIEW_LIST.add(new Review(21L,"movie","In my opinion, no film has touched me more than this one","Quite simply, the greatest film ever made. Humour, sadness, action, drama and a Vietnam film all rolled into one. I'm not a stone cold, heartless villain, but it takes a lot to make me cry when I watch a movie. Bambi's mother, I couldn't care less. Jimmy Stewart in, \"Oh, what a wonderful life,\" - yeah right! The Lion King, when Mufasa bites the big one - on the verge. But seriously - I bawled my big brown eyes out, on several occasions in this film. A real tear-jerker, and a wonderful character, played to perfection by Tom Hanks. Every bit as worthy for the Oscar as Rooney was to win the Premiership in 2007. I cannot say it enough: This is THE film of all time. Watch it, and you'll see.",4,REVIEW_CREATOR,REVIEW_CONTENT));

        List<Review> paginatedContent =  ps.infiniteScrollPagination(REVIEW_LIST,10,REVIEW_AMOUNT);
        Assert.assertNotNull(paginatedContent);
        Assert.assertEquals(REVIEW_LIST.subList(0,8),paginatedContent);
        Assert.assertEquals(8,paginatedContent.size());
    }

    @Test
    public void testPaginationValidTest(){
        boolean inValid = ps.checkPagination(40,3,CONTENT_AMOUNT);
        Assert.assertFalse(inValid);
    }

    @Test
    public void testPaginationInValidTest(){
        boolean inValid = ps.checkPagination(30,4,CONTENT_AMOUNT);
        Assert.assertTrue(inValid);
    }

    @Test
    public void amountOfContentPagesTest(){
        int pageAmount = ps.amountOfContentPages(21,CONTENT_AMOUNT);
        Assert.assertEquals(2,pageAmount);
    }





}
