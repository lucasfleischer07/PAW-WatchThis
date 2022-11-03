package ar.edu.itba.paw.models;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="review")
public class Review {
    @Column(name = "reviewid",columnDefinition = "INT")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "review_reviewid_seq1")
    @SequenceGenerator(name= "review_reviewid_seq1",sequenceName = "review_reviewid_seq1",allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private int rating;
    @Formula(value = "(SELECT coalesce(count(CASE WHEN r.upvote THEN 1 END),0) - coalesce(count(CASE WHEN r.downvote THEN 1 END),0) FROM reputation  r where r.reviewid=reviewid)")
    private int reputation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private User creator;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentid")
    private Content content;

    @OneToMany(mappedBy = "review")
    private Set<Reputation> userVotes;


    public Review(Long id, String type, String name, String description, Integer rating,User creator,Content content) {
        this.name = name;
        this.description = description;
        this.id=id;
        this.type = type;
        this.content=content;
        this.rating = rating;
        this.creator=creator;

    }
    public Review(String type, String name, String description, Integer rating,User creator,Content content) {
        this(null,type,name,description,rating,creator,content);

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

    public Content getContent() {
        return content;
    }

    public void setContentId(Content content) {
        this.content = content;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public User getCreator(){return this.creator;}
    public void setCreator(User user){this.creator=user;}

    public void setContent(Content content) {
        this.content = content;
    }

    @Transient
    public int getReputation() {return reputation;}

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public Set<Reputation> getUserVotes() {
        return userVotes;
    }
}
