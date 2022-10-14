package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "userdata")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userdata_userid_seq")
    @SequenceGenerator(name= "userdata_userid_seq",sequenceName = "userdata_userid_seq",allocationSize = 1)
    private Long id;
    @Column
    private Long reputation;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(unique = true,nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
    @Column
    private byte[] image;

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

    @ManyToMany
    private Set<Review> userReviews;

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
        return reputation;
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


    public byte[] getImage(){ return image;}

}
