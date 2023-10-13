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
    private String user;
    private String content;
    private String commentUrl;
    private String reviewReporters;
    private String reportReason;
    private int reportAmount;


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
        this.id = review.getId();
        this.name = review.getName();
        this.description = review.getDescription();
        this.rating = review.getRating();
        this.reputation = review.getReputation();
        this.type = review.getType();
        this.reportAmount = review.getReportAmount();
        this.reportReason = review.getReportReasons();
        this.reviewReporters = review.getReporterUsernames();

        this.user =  uriInfo.getBaseUriBuilder().path("users").path(String.valueOf(review.getUser().getId())).build().toString();
        this.content = uriInfo.getBaseUriBuilder().path("content").path(String.valueOf(review.getContent().getId())).build().toString();
        this.commentUrl = uriInfo.getBaseUriBuilder().path("comments").queryParam("reviewId", review.getId()).build().toString();
//        Lo que estaha antes
//        this.user = new UserDto(uriInfo, review.getUser());
//        this.content = new ContentDto(uriInfo, review.getContent());

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReportAmount() {
        return reportAmount;
    }

    public void setReportAmount(int reportAmount) {
        this.reportAmount = reportAmount;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }
}
