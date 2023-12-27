package ar.edu.itba.paw.models;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="review")
public class Review {
    @Column(name = "reviewid",columnDefinition = "INT")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "review_reviewid_seq1")
    @SequenceGenerator(name= "review_reviewid_seq1",sequenceName = "review_reviewid_seq1",allocationSize = 1)
    private long id;
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
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentid")
    private Content content;

    @OneToMany(mappedBy = "review",fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<Reputation> userVotes;

    @OneToMany(mappedBy = "review",fetch = FetchType.LAZY,orphanRemoval = true)
    @OrderBy(value = "commentid ASC ")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "review",orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<ReviewReport> reviewReports;

    @Formula(value = "(select coalesce(string_agg(u.name,' '),'') from Userdata u join ReviewReport r on u.userid=r.userid where r.reviewid=reviewid)")
    private String  reporterUsernames;
    @Transient
    private int reportAmount=0;
    @Formula(value = "(select coalesce(string_agg(r.reportreason,' '),'') from ReviewReport r  where r.reviewid=reviewid)")
    private String reportReasons;
    @PostLoad
    private void onLoad(){
        reportAmount=getReports().size();
    }

    public Review(Long id, String type, String name, String description, Integer rating,User creator,Content content) {
        this.name = name;
        this.description = description;
        this.id=id;
        this.type = type;
        this.content=content;
        this.rating = rating;
        this.user=creator;

    }
    public Review(String type, String name, String description, Integer rating,User creator,Content content) {
        this(0L,type,name,description,rating,creator,content);

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

    public Set<Comment> getComments() {
        return comments;
    }

    public Set<ReviewReport> getReports() {
        return reviewReports;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public User getUser(){return this.user;}
    public void setUser(User user){this.user=user;}

    public void setContent(Content content) {
        this.content = content;
    }

    public int getReportAmount() {
        return reportAmount;
    }

    @Transient
    public int getReputation() {return reputation;}

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public Set<Reputation> getUserVotes() {
        return userVotes;
    }

    public String getReportReasons() {
        return reportReasons;
    }

    public Set<ReviewReport> getReviewReports() {
        return reviewReports;
    }

    public void setReviewReports(Set<ReviewReport> reviewReports) {
        this.reviewReports = reviewReports;
    }

    @Transient
    public String getReporterUsernames() {
        return reporterUsernames;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Review other = (Review) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
