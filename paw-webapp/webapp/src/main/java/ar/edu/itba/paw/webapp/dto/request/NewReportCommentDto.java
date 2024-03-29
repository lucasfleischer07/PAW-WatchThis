package ar.edu.itba.paw.webapp.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class NewReportCommentDto {
    @NotNull(message = "...")
    private String reportType;

    @NotNull(message = "...")
    @Min(0)
    private long userId;

    public String getReportType() {
        return reportType;
    }
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
