package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.Comment;

import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.stream.Collectors;

public class LongDto {
    private long id;

    public static Collection<LongDto> mapLongToLongDto(Collection<Long> ids) {
        return ids.stream().map(c -> new LongDto(c)).collect(Collectors.toList());
    }

    public LongDto() {
        // For Jersey
    }

    public LongDto(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
