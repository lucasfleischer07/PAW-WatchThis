package ar.edu.itba.paw.models;

public class Movie {
    private long movieid;
    private String name;
    private String image;
    private String description;
    private String released;
    private String genre;
    private String creator;
    private String duration;

    public Movie(long movieid, String name, String image, String description, String released, String genre, String creator, String duration) {
        this.movieid = movieid;
        this.name = name;
        this.image = image;
        this.description = description;
        this.released = released;
        this.genre = genre;
        this.creator = creator;
        this.duration = duration;
    }

    public long getMovieId() {
        return movieid;
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
}
