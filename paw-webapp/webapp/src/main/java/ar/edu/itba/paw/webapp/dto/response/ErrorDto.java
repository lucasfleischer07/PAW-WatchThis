package ar.edu.itba.paw.webapp.dto.response;

public class ErrorDto {
    private String message;

    public ErrorDto() {
        // Jersey, do not use
    }

    public ErrorDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
