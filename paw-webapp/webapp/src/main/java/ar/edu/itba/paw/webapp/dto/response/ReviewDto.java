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

    public ReviewDto(UriInfo uriInfo, Review review) {
        this.commentUrl = uriInfo.getBaseUriBuilder().path("review").path(String.valueOf(review.getId())).build().toString();
        this.id = review.getId();
        this.name = review.getName();
        this.description = review.getDescription();
        this.rating = review.getRating();
        this.reputation = review.getReputation();
        this.type = review.getType();
        this.user = new UserDto(uriInfo, review.getUser());
        
    }
}
