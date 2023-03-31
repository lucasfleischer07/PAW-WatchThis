package ar.edu.itba.paw.webapp.dto.request;

import javax.validation.constraints.NotNull;

public class NewReportCommentDto {
    @NotNull(message = "...")
    private String reportType;
    public String getReportType() {
        return reportType;
    }
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

}
