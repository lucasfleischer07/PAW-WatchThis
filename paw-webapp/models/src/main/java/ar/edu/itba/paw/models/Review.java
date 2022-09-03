package ar.edu.itba.paw.models;

public class Review {
    private String name, description, userName, email, type;
    private long id;
    private Long reviewId;

    public Review(String name, String description, String userName, String email, long id, String type, Long reviewId) {
        this.name = name;
        this.description = description;
        this.userName = userName;
        this.email = email;
        this.id=id;
        this.type = type;
        this.reviewId=reviewId;

    }
    public Long getReviewId(){
        return reviewId;
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

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getType() { return type;}

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setType(String type) { this.type = type;}

    public void setReviewId(Long reviewId){
        this.reviewId=reviewId;
    }
}
