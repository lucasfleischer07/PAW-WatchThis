package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name="review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "review_reviewid_seq1")
    @SequenceGenerator(name= "review_reviewid_seq1",sequenceName = "review_reviewid_seq1",allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private Long contentId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String contentName;
    @Column(nullable = false)
    private Integer rating;

    public Review(Long id, String type, Long contentId, String name, String description, Integer rating,String userName,String contentName) {
        this.name = name;
        this.description = description;
        this.id=id;
        this.type = type;
        this.contentId=contentId;
        this.rating = rating;
        this.userName=userName;
        this.contentName=contentName;

    }
    public Review(String type, Long contentId, String name, String description, Integer rating,String userName,String contentName) {
        this.name = name;
        this.description = description;
        this.id=null;
        this.type = type;
        this.contentId=contentId;
        this.rating = rating;
        this.userName=userName;
        this.contentName=contentName;

    }

    /* package */ Review() {
// Just for Hibernate, we love you!
    }


    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }

    public String getType() { return type;}

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setType(String type) { this.type = type;}


    public void setId(Long id) {
        this.id = id;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getUserName(){return this.userName;}
    public void setUserName(String userName){this.userName=userName;}

    public String getContentName(){return this.contentName;}
    public void setContentName(String contentName){this.contentName=contentName;}

}
