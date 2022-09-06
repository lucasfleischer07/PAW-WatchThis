package ar.edu.itba.paw.models;

public class Review {
    private String name, description, type;
    private Long id, serieId, movieId, userId;

    public Review(Long id, String type, Long movieId, Long serieId, Long userId, String name, String description) {
        this.name = name;
        this.description = description;
        this.id=id;
        this.type = type;
        this.serieId=serieId;
        this.movieId=movieId;
        this.userId=userId;

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

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getSerieId() {
        return serieId;
    }

    public void setSerieId(Long serieId) {
        this.serieId = serieId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
