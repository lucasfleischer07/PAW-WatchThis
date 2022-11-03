package ar.edu.itba.paw.models;

import org.hibernate.annotations.Formula;

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

    @Formula(value = "(SELECT coalesce(count(CASE WHEN r.upvote THEN 1 END),0) - coalesce(count(CASE WHEN r.downvote THEN 1 END),0) FROM reputation r join review r2 on r2.reviewid = r.reviewid  where r2.userid=userid)")
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

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true)
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

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Reputation> userVotes;


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

    @Transient
    public Long getReputation() {
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

    public Set<Reputation> getUserVotes() {
        return userVotes;
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
