package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.*;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.stream.Collectors;

public class CommentReportDto {

    private long id;
    private UserDto user;
    private CommentDto comment;
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

    public CommentReportDto(UriInfo url, CommentReport commentReport){
        //Este path deberia ir con un metodo delete
//        Lucas:La acabo de hacer, solo habria que verificar
        this.eliminateComment = url.getBaseUriBuilder().path("comments").path("delete").path(String.valueOf(commentReport.getComment().getCommentId())).build().toString();
        //Este path tambien deberia ser con un metodo delete
//        Lucas:La acabo de hacer, solo habria que verificar
        this.dismissReport = url.getBaseUriBuilder().path("reports").path("deleteReport").path("comment").path(String.valueOf(commentReport.getComment().getReview().getContent().getId())).build().toString();
        this.id = commentReport.getId();
        this.user = new UserDto(url,commentReport.getUser());
        this.comment = new CommentDto(url,commentReport.getComment());
        this.reportReason = commentReport.getReportReason();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public CommentDto getComment() {
        return comment;
    }

    public void setComment(CommentDto comment) {
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
}
