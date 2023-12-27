package serviceTests;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Reputation;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistance.ContentDao;
import ar.edu.itba.paw.persistance.ReviewDao;
import ar.edu.itba.paw.services.ContentServiceImpl;
import ar.edu.itba.paw.services.ReviewServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl rs;

    @Mock
    private ReviewDao mockDao;

    private static final Long USER_ID = 15L;
    private static final String EMAIL = "user@gmail.com";
    private static final String NAME = "user";
    private static final String PASSWORD = "pass1234";
    private static final String ROLE = "USER";
    private static final Content CONTENT = new Content(7L,"Harry Potter",null,"Harry, Ron, and Hermione search for Voldemort's remaining Horcruxes in their effort to destroy the Dark Lord as the final battle rages on at Hogwarts.","2003","Action","David Yates","3 hours",130,"movie");
    private static final User USER = new User(USER_ID,EMAIL,NAME,PASSWORD,0L,null,ROLE);
    private static final User REVIEW_CREATOR = new User("Juan","juan@gmail.com","12345678");
    private static final Content REVIEW_CONTENT = new Content(1L,"The Lord of The Rings",null,"Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.","2003","Action","Peter Jackson","3 hours",130,"movie");


    @Test
    public void sortReviewsCorrectTest(){
        User ownerUser = new User(USER_ID,EMAIL,NAME,PASSWORD,0L,null,ROLE);
        User notOwnerUser = new User(5L,"notuser@gmail.com","notuser","pass123456",0L,null,ROLE);
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(new Review(5L,"movie","best movie","Very goood movie, incredible cinematography",4,notOwnerUser,CONTENT));
        reviewList.add(new Review(7L,"serie","incredible movie","Very goood movie, incredible cinematography",4,ownerUser,CONTENT));
        reviewList.add(new Review(42L,"movie","amazing movie","Very goood movie, incredible cinematography",4,notOwnerUser,CONTENT));
        reviewList.add(new Review(35L,"serie","trash movie","Very goood movie, incredible cinematography",4,ownerUser,CONTENT));
        reviewList = rs.sortReviews(ownerUser,reviewList);
        Assert.assertEquals(ownerUser,reviewList.get(0).getUser());
        Assert.assertEquals(ownerUser,reviewList.get(1).getUser());
        Assert.assertNotEquals(ownerUser,reviewList.get(2).getUser());
        Assert.assertNotEquals(ownerUser,reviewList.get(3).getUser());
    }

    @Test
    public void sortReviewsWithNullUserTest(){
        User ownerUser = new User(USER_ID,EMAIL,NAME,PASSWORD,0L,null,ROLE);
        User notOwnerUser = new User(5L,"notuser@gmail.com","notuser","pass123456",0L,null,ROLE);
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(new Review(5L,"movie","best movie","Very goood movie, incredible cinematography",4,notOwnerUser,CONTENT));
        reviewList.add(new Review(7L,"serie","incredible movie","Very goood movie, incredible cinematography",4,ownerUser,CONTENT));
        reviewList.add(new Review(42L,"movie","amazing movie","Very goood movie, incredible cinematography",4,notOwnerUser,CONTENT));
        reviewList.add(new Review(35L,"serie","trash movie","Very goood movie, incredible cinematography",4,ownerUser,CONTENT));
        List<Review> checkList = new ArrayList<>();
        checkList.addAll(reviewList);
        reviewList = rs.sortReviews(null,reviewList);
        Assert.assertEquals(checkList,reviewList);

    }


    // TODO: Adaptar este test para que no sea mas sobre ids sino que sea para reviews enteras
    @Test
    public void userLikeAndDislikeTest(){
        Set<Reputation> reputations = new HashSet<>();
        reputations.add(new Reputation(USER,new Review(1L,"movie","Incredible movie","This isn't just a beautifully crafted gangster film. Or an outstanding family portrait, for that matter. An amazing period piece. A character study. A lesson in filmmaking and an inspiration to generations of actors, directors, screenwriters and producers. For me, this is more: this is the definitive film. 10 stars out of 10.",4,REVIEW_CREATOR,REVIEW_CONTENT),true));
        reputations.add(new Reputation(USER,new Review(14L,"movie","Worst time spent in my life","Unmarketable upon box office release, Darabount's masterpiece tanked and seemed destined to obscurity. Only after the video release, did 'Shawshawk' reap the praise it so richly deserved. Is it one of the greatest films ever made? Without a doubt, yes.",4,REVIEW_CREATOR,REVIEW_CONTENT),true));
        reputations.add(new Reputation(USER,new Review(12L,"movie","Perfect, down to the last minute detail","Exceleten pelicula, muy buenos actores",4,REVIEW_CREATOR,REVIEW_CONTENT),false));
        reputations.add(new Reputation(USER,new Review(18L,"movie","Estoy fascinado","Maravillosa cinematografia y nivel de produccion",4,REVIEW_CREATOR,REVIEW_CONTENT),true));
        reputations.add(new Reputation(USER,new Review(3L,"movie","Nailed it!","To my surprise they absolutely nailed it. Cumberbatch is a fantastic choice for Sherlock Holmes-he is physically right (he fits the traditional reading of the character) and he is a damn good actor. Martin Freeman, about whom I wasn't sure at first, is an excellent foil for Holmes without being the dumb sidekick that Dr Watson has often been. I thought that this series would not work, particularly after Robert Downey's interesting take on Conan Doyle's characterisation. I have been proved so wrong-it moved along at a good pace and held the attention brilliantly. My wife started by saying she didn't like it but by the end of the episode she was as enthralled as I. We are both looking forward to the rest of the series, if it is as entertaining as the first story. I was disappointed to read some reviews here that did not love it. Methinks they are too jaded to enjoy anything.",4,REVIEW_CREATOR,REVIEW_CONTENT),false));

        rs.userLikeAndDislikeReviewsId(reputations);
        List<Review> checkUpvoteList = new ArrayList<>();
        checkUpvoteList.add(new Review(1L,"movie","Incredible movie","This isn't just a beautifully crafted gangster film. Or an outstanding family portrait, for that matter. An amazing period piece. A character study. A lesson in filmmaking and an inspiration to generations of actors, directors, screenwriters and producers. For me, this is more: this is the definitive film. 10 stars out of 10.",4,REVIEW_CREATOR,REVIEW_CONTENT));
        checkUpvoteList.add(new Review(14L,"movie","Worst time spent in my life","Unmarketable upon box office release, Darabount's masterpiece tanked and seemed destined to obscurity. Only after the video release, did 'Shawshawk' reap the praise it so richly deserved. Is it one of the greatest films ever made? Without a doubt, yes.",4,REVIEW_CREATOR,REVIEW_CONTENT));
        checkUpvoteList.add(new Review(18L,"movie","Estoy fascinado","Maravillosa cinematografia y nivel de produccion",4,REVIEW_CREATOR,REVIEW_CONTENT));

        List<Review> checkDownvoteList = new ArrayList<>();
        checkDownvoteList.add(new Review(3L,"movie","Nailed it!","To my surprise they absolutely nailed it. Cumberbatch is a fantastic choice for Sherlock Holmes-he is physically right (he fits the traditional reading of the character) and he is a damn good actor. Martin Freeman, about whom I wasn't sure at first, is an excellent foil for Holmes without being the dumb sidekick that Dr Watson has often been. I thought that this series would not work, particularly after Robert Downey's interesting take on Conan Doyle's characterisation. I have been proved so wrong-it moved along at a good pace and held the attention brilliantly. My wife started by saying she didn't like it but by the end of the episode she was as enthralled as I. We are both looking forward to the rest of the series, if it is as entertaining as the first story. I was disappointed to read some reviews here that did not love it. Methinks they are too jaded to enjoy anything.",4,REVIEW_CREATOR,REVIEW_CONTENT));
        checkDownvoteList.add(new Review(12L,"movie","Perfect, down to the last minute detail","Exceleten pelicula, muy buenos actores",4,REVIEW_CREATOR,REVIEW_CONTENT));

        Set<Review> upVotes = rs.getUserLikeReviews();
        Set<Review> downVotes = rs.getUserDislikeReviews();

        Assert.assertNotNull(upVotes);
        Assert.assertNotNull(downVotes);

        Assert.assertTrue (checkUpvoteList.containsAll(upVotes));
        Assert.assertTrue(upVotes.containsAll(checkUpvoteList));

        Assert.assertTrue(checkDownvoteList.containsAll(downVotes));
        Assert.assertTrue(downVotes.containsAll(checkDownvoteList));

    }



}
