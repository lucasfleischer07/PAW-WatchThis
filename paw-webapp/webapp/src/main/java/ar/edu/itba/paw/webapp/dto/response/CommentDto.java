package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.CommentReport;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;

import javax.persistence.*;
import javax.ws.rs.core.UriInfo;
import java.util.Set;

public class CommentDto {

    private long commentId;
    private UserDto user;
    private String text;

    private String commentReportersUrl;

    public CommentDto(UriInfo uriInfo, Comment comment) {
        this.commentReportersUrl = uriInfo.getBaseUriBuilder().path("reports").path("comment").path(String.valueOf(comment.getCommentId())).build().toString();
        this.text = comment.getText();
        this.commentId = comment.getCommentId();
        this.user = new UserDto(uriInfo, comment.getUser());
    }


    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommentReportersUrl() {
        return commentReportersUrl;
    }

    public void setCommentReportersUrl(String commentReportersUrl) {
        this.commentReportersUrl = commentReportersUrl;
    }




}
