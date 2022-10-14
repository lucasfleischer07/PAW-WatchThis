package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "content_id_seq")
    @SequenceGenerator(name= "content_id_seq",sequenceName = "content_id_seq",allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String released;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private String creator;
    @Column(nullable = false)
    private String duration;
    @Column(nullable = false)
    private String type;
    @Column
    private byte[] image;
    @Column(nullable = false)
    private Integer rating;
    @Column(nullable = false)
    private Integer reviewsAmount;

    @OneToMany
    @JoinColumn(name = "contentid")
    private List<Review> contentReviews;

    public Content(long id, String name, byte[] image, String description, String released, String genre, String creator, String duration,String type,Integer rating,Integer reviewsAmount) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.released = released;
        this.genre = genre;
        this.creator = creator;
        this.duration = duration;
        this.type= type;
        this.rating = rating;
        this.reviewsAmount=reviewsAmount;
    }
    public Integer getReviewsAmount(){
        return reviewsAmount;
    }

    public Integer getRating(){
        return rating;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getReleased() {
        return released;
    }

    public String getGenre() {
        return genre;
    }

    public String getCreator() {
        return creator;
    }

    public String getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }
}
