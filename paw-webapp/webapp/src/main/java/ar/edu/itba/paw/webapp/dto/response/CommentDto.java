package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.Comment;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.stream.Collectors;

public class CommentDto {

    private long commentId;
    private UserDto user;
    private String text;

    private String commentReportersUrl;

    public static Collection<CommentDto> mapCommentToCommentDto(UriInfo uriInfo, Collection<Comment> comments) {
        return comments.stream().map(c -> new CommentDto(uriInfo, c)).collect(Collectors.toList());
    }

    public static UriBuilder getCommentUriBuilder(Comment comment, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path("commentAdd").path(String.valueOf(comment.getCommentId()));
    }

    public static UriBuilder getCommentReportedUriBuilder(Comment comment, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path("reports").path("comment").path(String.valueOf(comment.getCommentId()));
    }

    public CommentDto() {
        // For Jersey
    }

    public CommentDto(UriInfo uriInfo, Comment comment) {
        this.commentReportersUrl = getCommentReportedUriBuilder(comment, uriInfo).toString();
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
