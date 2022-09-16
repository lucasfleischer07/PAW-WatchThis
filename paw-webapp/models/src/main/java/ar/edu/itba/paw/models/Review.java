package ar.edu.itba.paw.models;

public class Review {
    private String name, description, type,userName;
    private Long id, contentId, userId, rating;

    public Review(Long id, String type, Long contentId, Long userId, String name, String description, Long rating,String userName) {
        this.name = name;
        this.description = description;
        this.id=id;
        this.type = type;
        this.contentId=contentId;
        this.userId=userId;
        this.rating = rating;
        this.userName=userName;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public String getUserName(){return this.userName;}
    public void setUserName(String userName){this.userName=userName;}

}
