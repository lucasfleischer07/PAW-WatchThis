package ar.edu.itba.paw.models;

public class Content {
    private long id;
    private String name, image, description, released, genre, creator, duration, type;


    public Content(long id, String name, String image, String description, String released, String genre, String creator, String duration,String type) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.released = released;
        this.genre = genre;
        this.creator = creator;
        this.duration = duration;
        this.type= type;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
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
