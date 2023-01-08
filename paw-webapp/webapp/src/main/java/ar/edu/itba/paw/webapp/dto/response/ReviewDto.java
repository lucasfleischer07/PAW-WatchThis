package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.Review;

import javax.ws.rs.core.UriInfo;

public class ReviewDto {
    private long id;
    private String name;
    private String description;
    private Integer rating;
    private Integer reputation;
    private String type;
    private UserDto user;
    
    private String commentUrl;
    private String reviewReportersUrl;

    public ReviewDto(UriInfo uriInfo, Review review) {
        this.commentUrl = uriInfo.getBaseUriBuilder().path("review").path(String.valueOf(review.getId())).build().toString();
        this.reviewReportersUrl = uriInfo.getBaseUriBuilder().path("reports").path("review").path(String.valueOf(review.getId())).build().toString();
        this.id = review.getId();
        this.name = review.getName();
        this.description = review.getDescription();
        this.rating = review.getRating();
        this.reputation = review.getReputation();
        this.type = review.getType();
        this.user = new UserDto(uriInfo, review.getUser());

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getReputation() {
        return reputation;
    }

    public void setReputation(Integer reputation) {
        this.reputation = reputation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getCommentUrl() {
        return commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }

    public String getReviewReportersUrl() {
        return reviewReportersUrl;
    }

    public void setReviewReportersUrl(String reviewReportersUrl) {
        this.reviewReportersUrl = reviewReportersUrl;
    }


}
