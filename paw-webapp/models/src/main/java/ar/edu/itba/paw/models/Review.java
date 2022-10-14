package ar.edu.itba.paw.models;

public class Review {
    private String name, description, type,userName,contentName;
    private Long id, contentId;
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
        this.id=id;
        this.type = type;
        this.contentId=contentId;
        this.rating = rating;
        this.userName=userName;
        this.contentName=contentName;

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
