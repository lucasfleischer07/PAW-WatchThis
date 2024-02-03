package ar.edu.itba.paw.models;

public enum ReportReason {
    Spam("Span"),
    Insult("Insult"),
    Inappropriate("Inappropriate"),
    Unrelated("Unrelated"),
    Other("Other");

    private String reason;

    ReportReason (String reason){
        this.reason = reason;
    }

    public String getReason(){
        return reason;
    }
}
