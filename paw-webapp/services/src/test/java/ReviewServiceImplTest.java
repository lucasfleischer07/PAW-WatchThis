import ar.edu.itba.paw.models.Content;
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
import java.util.List;

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

    @Test
    public void sortReviewsCorrect(){
        User ownerUser = new User(USER_ID,EMAIL,NAME,PASSWORD,0L,null,ROLE);
        User notOwnerUser = new User(5L,"notuser@gmail.com","notuser","pass123456",0L,null,ROLE);
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(new Review(5L,"movie","best movie","Very goood movie, incredible cinematography",4,notOwnerUser,CONTENT));
        reviewList.add(new Review(7L,"serie","incredible movie","Very goood movie, incredible cinematography",4,ownerUser,CONTENT));
        reviewList.add(new Review(42L,"movie","amazing movie","Very goood movie, incredible cinematography",4,notOwnerUser,CONTENT));
        reviewList.add(new Review(35L,"serie","trash movie","Very goood movie, incredible cinematography",4,ownerUser,CONTENT));
        reviewList = rs.sortReviews(ownerUser,reviewList);
        Assert.assertEquals(ownerUser,reviewList.get(0).getCreator());
        Assert.assertEquals(ownerUser,reviewList.get(1).getCreator());
        Assert.assertNotEquals(ownerUser,reviewList.get(2).getCreator());
        Assert.assertNotEquals(ownerUser,reviewList.get(3).getCreator());
    }

    @Test
    public void sortReviewsWithNullUser(){
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



}
