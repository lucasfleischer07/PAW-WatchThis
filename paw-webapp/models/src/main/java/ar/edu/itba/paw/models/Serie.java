package ar.edu.itba.paw.models;

public class Serie {
    private long serieid;
    private String name;
    private String image;
    private String description;
    private String released;
    private String genre;
    private String creator;
    private String duration;

    public Serie(long serieid, String name, String image, String description, String released, String genre, String creator, String duration) {
        this.serieid = serieid;
        this.name = name;
        this.image = image;
        this.description = description;
        this.released = released;
        this.genre = genre;
        this.creator = creator;
        this.duration = duration;
    }

    public long getSerieId() {
        return serieid;
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

    public String getAvgDuration() {
        return duration;
    }
}
