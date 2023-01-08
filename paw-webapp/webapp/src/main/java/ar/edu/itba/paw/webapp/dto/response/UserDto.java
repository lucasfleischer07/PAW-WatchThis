package ar.edu.itba.paw.webapp.dto.response;

import javax.ws.rs.core.UriInfo;
import ar.edu.itba.paw.models.User;

public class UserDto {
    private String username;
    private String email;
    private Long id;
    private Long reputation;
    private byte[] image;
    private String role;


    private String userwatchListURL;
    private String userViewedListURL;
    private String userReviews;


    public UserDto(UriInfo url, User user) {
        this.userwatchListURL = url.getBaseUriBuilder().path("watchlist").path(String.valueOf(user.getId())).build().toString();
        this.userViewedListURL = url.getBaseUriBuilder().path("viewedlist").path(String.valueOf(user.getId())).build().toString();
        this.userReviews = url.getBaseUriBuilder().path("user").path(String.valueOf(content.getId())).path("reviews").build().toString();
        this.email = user.getEmail();
        this.username = user.getUserName();
        this.id = user.getId();
        this.reputation = user.getReputation();
        this.image = user.getImage();
        this.role = user.getRole();
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserwatchListURL() {
        return userwatchListURL;
    }

    public void setUserwatchListURL(String userwatchListURL) {
        this.userwatchListURL = userwatchListURL;
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
}
