package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.*;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.stream.Collectors;

public class CommentReportDto {
    private long id;
    private String user;
    private String comment;
    private String review;
    private String content;
    private final String type = "comment";
    private String commentReportersUrl;
    private String reportReason;
    private int reportAmount;

    public static Collection<CommentReportDto> mapCommentReportToCommentReportDto(UriInfo uriInfo, Collection<CommentReport> commentsReported) {
        return commentsReported.stream().map(cr -> new CommentReportDto(uriInfo, cr)).collect(Collectors.toList());
    }

    public static UriBuilder getCommentReportedUriBuilder(Comment comment, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path("reports").path("comment").path(String.valueOf(comment.getCommentId()));
    }

    public CommentReportDto() {
        // For Jersey
    }

    public CommentReportDto(UriInfo uriInfo, CommentReport commentReport){
        this.id = commentReport.getId();
        this.user = uriInfo.getBaseUriBuilder().path("users").path(String.valueOf(commentReport.getUser().getId())).build().toString();
        this.review =  uriInfo.getBaseUriBuilder().path("reviews").path(String.valueOf(commentReport.getComment().getReview().getId())).build().toString();
        this.content =  uriInfo.getBaseUriBuilder().path("content").path(String.valueOf(commentReport.getComment().getReview().getContent().getId())).build().toString();
        this.comment = uriInfo.getBaseUriBuilder().path("comments").path(String.valueOf(commentReport.getComment().getCommentId())).build().toString();
        this.reportAmount = commentReport.getComment().getReportAmount();
        this.reportReason = commentReport.getComment().getReportReasons();
        this.commentReportersUrl = uriInfo.getBaseUriBuilder().path("comments").path("reports").build().toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentReportersUrl() {
        return commentReportersUrl;
    }

    public void setCommentReportersUrl(String commentReportersUrl) {
        this.commentReportersUrl = commentReportersUrl;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public int getReportAmount() {
        return reportAmount;
    }

    public void setReportAmount(int reportAmount) {
        this.reportAmount = reportAmount;
    }
}
