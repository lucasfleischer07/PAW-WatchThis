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
    private ReportReason reportReason;
    private String eliminateComment;
    private String dismissReport;

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
        this.reportReason = commentReport.getReportReason();
        this.user = uriInfo.getBaseUriBuilder().path("users").path(String.valueOf(commentReport.getUser().getId())).build().toString();
        this.review =  uriInfo.getBaseUriBuilder().path("reviews").path(String.valueOf(commentReport.getComment().getReview().getId())).build().toString();
        this.content =  uriInfo.getBaseUriBuilder().path("content").path(String.valueOf(commentReport.getComment().getReview().getContent().getId())).build().toString();

        // TODO: Testear bien este, puede que este mal
        this.comment = uriInfo.getBaseUriBuilder().path("comments").path(String.valueOf(commentReport.getComment().getCommentId())).build().toString();

//        TODO: Siento que estas 3 estan al re pedo, para eliminar es el mismo pathe pero cambia el delete y eso
//        this.eliminateComment = url.getBaseUriBuilder().path("comments").path("delete").path(String.valueOf(commentReport.getComment().getCommentId())).build().toString();
//        this.dismissReport = url.getBaseUriBuilder().path("reports").path("deleteReport").path("comment").path(String.valueOf(commentReport.getComment().getReview().getContent().getId())).build().toString();
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

    public ReportReason getReportReason() {
        return reportReason;
    }

    public void setReportReason(ReportReason reportReason) {
        this.reportReason = reportReason;
    }

    public String getEliminateComment() {
        return eliminateComment;
    }

    public void setEliminateComment(String eliminateComment) {
        this.eliminateComment = eliminateComment;
    }

    public String getDismissReport() {
        return dismissReport;
    }

    public void setDismissReport(String dismissReport) {
        this.dismissReport = dismissReport;
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
}
