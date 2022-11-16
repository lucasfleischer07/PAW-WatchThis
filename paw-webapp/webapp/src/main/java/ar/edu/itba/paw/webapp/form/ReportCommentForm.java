package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotNull;

public class ReportCommentForm {
    @NotNull
    private String reportType;

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

}
