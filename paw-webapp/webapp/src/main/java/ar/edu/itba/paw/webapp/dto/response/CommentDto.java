package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.Comment;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.stream.Collectors;

public class CommentDto {
    private long commentId;
    private String user;
    private String text;

    public static Collection<CommentDto> mapCommentToCommentDto(UriInfo uriInfo, Collection<Comment> comments) {
        return comments.stream().map(c -> new CommentDto(uriInfo, c)).collect(Collectors.toList());
    }

    public static UriBuilder getCommentUriBuilder(Comment comment, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path("comments").path(String.valueOf(comment.getReview().getId()));
    }

    public CommentDto() {
        // For Jersey
    }

    public CommentDto(UriInfo uriInfo, Comment comment) {
        this.text = comment.getText();
        this.commentId = comment.getCommentId();
        this.user =  uriInfo.getBaseUriBuilder().path("users").path(String.valueOf(comment.getUser().getId())).build().toString();
    }


    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
