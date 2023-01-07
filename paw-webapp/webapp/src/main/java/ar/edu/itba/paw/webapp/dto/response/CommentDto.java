package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.Comment;

import javax.ws.rs.core.UriInfo;

public class CommentDto {
    private String comment;

    public CommentDto(UriInfo uriInfo, Comment comment) {
        this.comment = comment.getText();
    }
}
