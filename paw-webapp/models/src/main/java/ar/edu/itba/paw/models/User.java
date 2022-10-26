package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "userdata")
public class User {
    @Column(name = "userid",columnDefinition = "INT")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userdata_userid_seq")
    @SequenceGenerator(name= "userdata_userid_seq",sequenceName = "userdata_userid_seq",allocationSize = 1)
    private Long id;
    private Long reputation;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(name = "name",unique = true,nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
    @Column
    private byte[] image;

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "userid")
    private List<Review> userReviews;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "userwatchlist",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "contentid"))
    private List<Content> watchlist;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "userviewedlist",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "contentid"))
    private List<Content> viewedlist;


    public User(Long id, String email, String userName, String password, Long reputation, byte[] image, String role) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.reputation = reputation;
        this.image = image;
        this.role=role;
    }

    public User(String userName,String userEmail,String password){
        this(null,userEmail,userName,password,null,null,"user");
    }

    /* package */ User() {
// Just for Hibernate, we love you!
    }



    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Long getReputation() {
        List<Review> userReviews = getUserReviews();
        Long reputation = 0L;
        for(Review review : userReviews) {
            reputation += (review.getReputation());
        }
        if(reputation == 0) {
            return 0L;
        }
        reputation /= userReviews.size();
        return reputation;
    }

    public List<Content> getViewedList() {
        return viewedlist;
    }

    public List<Content> getWatchlist() {
        return watchlist;
    }

    public List<Review> getUserReviews() {
        return userReviews;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password=password;
    }
    public void setImage(byte[] image) {
        this.image=image;
    }
    public void setRole(String role){this.role=role;}

    public byte[] getImage(){ return image;}

}
