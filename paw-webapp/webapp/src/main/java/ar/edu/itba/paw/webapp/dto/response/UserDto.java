package ar.edu.itba.paw.webapp.dto.response;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import ar.edu.itba.paw.models.ReviewReport;
import ar.edu.itba.paw.models.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDto {
    private String username;
    private String email;
    private Long id;
    private Long reputation;
    private String image;
    private String role;
    private String userWatchListURL;
    private String userViewedListURL;
    private String userReviews;
    private String isLikeReviewsUrl;
    private String isDislikeReviewsUrl;

    public static Collection<UserDto> mapUserToUserDto(UriInfo uriInfo, Collection<User> user) {
        return user.stream().map(u -> new UserDto(uriInfo, u)).collect(Collectors.toList());
    }

    public static UriBuilder getUserUriBuilder(User user, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().clone().path("users").path(String.valueOf(user.getId()));
    }

    public UserDto() {
        // For Jersey
    }


    public UserDto(UriInfo url, User user) {
        this.userWatchListURL = url.getBaseUriBuilder().path("lists").path("watchList").path(String.valueOf(user.getId())).build().toString();
        this.userViewedListURL = url.getBaseUriBuilder().path("lists").path("viewedList").path(String.valueOf(user.getId())).build().toString();
        this.userReviews = url.getBaseUriBuilder().path("users").path(String.valueOf(user.getId())).path("reviews").build().toString();
        this.email = user.getEmail();
        this.username = user.getUserName();
        this.id = user.getId();
        this.reputation = user.getReputation();
        this.image = url.getBaseUriBuilder().path("users").path(String.valueOf(user.getId())).path("profileImage").build().toString();
        this.role = user.getRole();
        this.isLikeReviewsUrl = url.getBaseUriBuilder().path("users").path(String.valueOf(user.getId())).path("reviewsLikedByUser").build().toString();
        this.isDislikeReviewsUrl = url.getBaseUriBuilder().path("users").path(String.valueOf(user.getId())).path("reviewsDislikedByUser").build().toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReputation() {
        return reputation;
    }

    public void setReputation(Long reputation) {
        this.reputation = reputation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserWatchListURL() {
        return userWatchListURL;
    }

    public void setUserWatchListURL(String userWatchListURL) {
        this.userWatchListURL = userWatchListURL;
    }

    public String getUserViewedListURL() {
        return userViewedListURL;
    }

    public void setUserViewedListURL(String userViewedListURL) {
        this.userViewedListURL = userViewedListURL;
    }

    public String getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(String userReviews) {
        this.userReviews = userReviews;
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
