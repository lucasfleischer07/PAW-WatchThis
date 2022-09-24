package ar.edu.itba.paw.models;

public class Content {
    private long id;
    private String name, description, released, genre, creator, duration, type;
    private byte[] image;
    private Integer rating;
    private Integer reviewsAmount;


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
