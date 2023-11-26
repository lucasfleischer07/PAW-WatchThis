package ar.edu.itba.paw.webapp.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BasicRequestDto {

    @NotNull(message = "...")
    @Min(0)
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
