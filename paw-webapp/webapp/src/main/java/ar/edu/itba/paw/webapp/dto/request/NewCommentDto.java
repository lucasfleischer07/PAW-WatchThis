package ar.edu.itba.paw.webapp.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class NewCommentDto {
//    TODO: METER LOS MENSAJES DE ERROR EN CASO DE QUE ESTE VACIO
    @NotNull(message = "...")
    @Size(min = 5, max = 250, message = "...")
    @Pattern(regexp	= "([a-zA-Z0-9ñáéíóú!,.:;=+\n\\-_()?<>$%&#@{}\\[\\]|*\"'~/`^\\s]+)?", message = "...")
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
