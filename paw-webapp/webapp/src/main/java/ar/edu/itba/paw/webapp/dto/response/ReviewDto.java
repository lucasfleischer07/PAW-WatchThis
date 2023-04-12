package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.stream.Collectors;

public class ReviewDto {
    private long id;
    private String name;
    private String description;
    private Integer rating;
    private Integer reputation;
    private String type;
    private UserDto user;
    private ContentDto content;
    private String commentUrl;
    private String isLikeReviewsUrl;
    private String isDislikeReviewsUrl;

    private String reviewReporters;


    public static Collection<ReviewDto> mapReviewToReviewDto(UriInfo uriInfo, Collection<Review> reviews) {
        return reviews.stream().map(r -> new ReviewDto(uriInfo, r)).collect(Collectors.toList());
    }

    public static UriBuilder getReviewUriBuilder(Content content, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().clone().path("reviews").path(String.valueOf(content.getId()));
    }

    public ReviewDto() {
        // For Jersey
    }

    public ReviewDto(UriInfo uriInfo, Review review) {
        this.commentUrl = uriInfo.getBaseUriBuilder().path("comments").path(String.valueOf(review.getId())).build().toString();
        this.reviewReporters = review.getReporterUsernames();
        this.isLikeReviewsUrl = uriInfo.getBaseUriBuilder().path("reviews").path("likedByUsers").build().toString();
        this.isDislikeReviewsUrl = uriInfo.getBaseUriBuilder().path("reviews").path("dislikedByUsers").build().toString();
        this.id = review.getId();
        this.name = review.getName();
        this.description = review.getDescription();
        this.rating = review.getRating();
        this.reputation = review.getReputation();
        this.type = review.getType();
        this.user = new UserDto(uriInfo, review.getUser());
        this.content = new ContentDto(uriInfo, review.getContent());

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

    public String getReviewReporters() {
        return reviewReporters;
    }

    public void setReviewReporters(String reviewReporters) {
        this.reviewReporters = reviewReporters;
    }

    public ContentDto getContent() {
        return content;
    }

    public void setContent(ContentDto content) {
        this.content = content;
    }

    public String getIsLikeReviewsUrl() {
        return isLikeReviewsUrl;
    }

    public void setIsLikeReviewsUrl(String isLikeReviewsUrl) {
        this.isLikeReviewsUrl = isLikeReviewsUrl;
    }

    public String getIsDislikeReviewsUrl() {
        return isDislikeReviewsUrl;
    }

    public void setIsDislikeReviewsUrl(String isDislikeReviewsUrl) {
        this.isDislikeReviewsUrl = isDislikeReviewsUrl;
    }
}
