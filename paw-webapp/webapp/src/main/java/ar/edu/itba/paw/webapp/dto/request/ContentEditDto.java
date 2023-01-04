package ar.edu.itba.paw.webapp.dto.request;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ContentEditDto {
//    TODO: METER LOS MENSAJES DE ERROR EN CASO DE QUE ESTE VACIO
    @Size(min = 1, max = 100, message = "...")
    @Pattern(regexp	= "([a-zA-Z0-9ñáéíóú!,.:;=+\n\\-_()?<>$%&#@{}\\[\\]|*\"'~/`^\\s]+)?", message = "...")
    private String name;

    @Size(min = 20, max = 500, message = "...")
    @Pattern(regexp	= "([a-zA-Z0-9ñáéíóú!,.:;=+\n\\-_()?<>$%&#@{}\\[\\]|*\"'~/`^\\s]+)?", message = "...")
    private String description;

    @Size(min = 4, max = 4, message = "...")
    @Pattern(regexp = "(19[0-9][0-9]|20[01][0-9]|202[0-2])", message = "...")
    private String releaseDate;

    @NotEmpty(message = "...")
    private String[] genre;

    @Size(min = 4, max = 100, message = "...")
    private String creator;

    @Max(300)
    private Integer duration;

    @NotNull(message = "...")
    private String type;


    private MultipartFile contentPicture;

    public void setContentPicture(MultipartFile contentPicture) {
        this.contentPicture = contentPicture;
    }

    public MultipartFile getContentPicture() {
        return contentPicture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenre(String[] genre) {
        this.genre = genre;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String[] getGenre() {
        return genre;
    }

    public String getCreator() {
        return creator;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }
}
