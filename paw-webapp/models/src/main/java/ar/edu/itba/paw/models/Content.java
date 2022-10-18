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
    @Column(nullable = true)
    private byte[] image;
    @Column
    private Integer durationNum;

    @OneToMany(orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "contentid")
    List<Review> contentReviews;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "userwatchlist",
            joinColumns = @JoinColumn(name = "contentid",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "userid",referencedColumnName = "userid"))
    private List<User> watchlist;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "userviewedlist",
            joinColumns = @JoinColumn(name = "contentid",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "userid",referencedColumnName = "userid"))
    private List<User> viewedlist;


    public Content(Long id, String name, byte[] image, String description, String released, String genre, String creator, String duration,int durationNum,String type) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.released = released;
        this.genre = genre;
        this.creator = creator;
        this.duration = duration;
        this.type= type;
        this.durationNum=durationNum;

    }

    public Content(String name, byte[] image, String description, String released, String genre, String creator, String duration,int durationNum,String type) {
        this(null,name,image,description,released,genre,creator,duration,durationNum,type);
    }

    /* package */ Content() {
// Just for Hibernate, we love you!
    }
    public Integer getReviewsAmount(){
        return contentReviews.size();
    }

    public Integer getRating(){
        int count=0;
        int num=0;
        for (Review review:contentReviews
             ) {
            if(review.getRating()>0){
                count++;
                num+=review.getRating();
            }
        }
        return num/count;
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

    public Integer getDurationNum() {
        return durationNum;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public void setDurationNum(Integer durationNum) {
        this.durationNum = durationNum;
    }

    public List<Review> getContentReviews() {
        return contentReviews;
    }
}
