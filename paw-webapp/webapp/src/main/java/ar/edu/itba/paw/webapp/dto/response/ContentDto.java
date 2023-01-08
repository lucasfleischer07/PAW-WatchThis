package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.CommentReport;
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.stream.Collectors;


// /movies  ---> getAllMovies   ----> List<Content>   --->  List<Contentdto>
public class ContentDto {
    // Content fields
    private Long id;
    private String name;
    private String description;
    private String releaseDate;
    private String genre;
    private String creator;
    private String duration;
    private String type;
    private byte[] contentPicture;
    private Integer reviewsAmount;
    private Integer rating;

    // Content main url
    private String myUrl;
    private String reviewsUrl;
    private String contentReviewers;

    public static Collection<ContentDto> mapContentToContentDto(UriInfo uriInfo, Collection<Content> content, User user) {
        return content.stream().map(c -> new ContentDto(uriInfo, c, user)).collect(Collectors.toList());
    }

    public ContentDto() {
        // For Jersey
    }

//    TODO: Ver si le pasamos o no el user
    public ContentDto(UriInfo url, Content content, User user) {
        this.myUrl = url.getBaseUriBuilder().path("content").path(String.valueOf(content.getId())).build().toString();
        this.reviewsUrl = url.getBaseUriBuilder().path("reviews").path(String.valueOf(content.getId())).build().toString();
        this.contentReviewers = url.getBaseUriBuilder().path("content").path(String.valueOf(content.getId())).path("reviewers").build().toString();

        this.id = content.getId();
        this.name = content.getName();
        this.description = content.getDescription();
        this.genre = content.getGenre();
        this.creator = content.getCreator();
        this.releaseDate = content.getReleased();
        this.duration = content.getDuration();
        this.type = content.getType();
        this.contentPicture = content.getImage();
        this.reviewsAmount = content.getReviewsAmount();
        this.rating = content.getRating();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getContentPicture() {
        return contentPicture;
    }

    public void setContentPicture(byte[] contentPicture) {
        this.contentPicture = contentPicture;
    }

    public Integer getReviewsAmount() {
        return reviewsAmount;
    }

    public void setReviewsAmount(Integer reviewsAmount) {
        this.reviewsAmount = reviewsAmount;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getMyUrl() {
        return myUrl;
    }

    public void setMyUrl(String myUrl) {
        this.myUrl = myUrl;
    }

    public String getReviewsUrl() {
        return reviewsUrl;
    }

    public void setReviewsUrl(String reviewsUrl) {
        this.reviewsUrl = reviewsUrl;
    }

}
