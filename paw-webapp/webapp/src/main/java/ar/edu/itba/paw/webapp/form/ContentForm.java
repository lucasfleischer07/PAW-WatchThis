package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.ImageNotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

public class ContentForm {

    @Size(min = 1, max = 100)
    @Pattern(regexp	= "([a-zA-Z0-9ñáéíóú!,.:;=+\n\\-_()?<>$%&#@{}\\[\\]|*\"'~/`^\\s]+)?")
    private String name;

    @Size(min = 20, max = 2000)
    @Pattern(regexp	= "([a-zA-Z0-9ñáéíóú!,.:;=+\n\\-_()?<>$%&#@{}\\[\\]|*\"'~/`^\\s]+)?")
    private String description;

    @Size(min = 4, max = 4)
    @Pattern(regexp	= "[1-2][90][0-9][0-9]")
    private String releaseDate;

    @NotNull
    private String genre;

    @Size(min = 4, max = 100)
    private String creator;

    @Max(300)
    @Min(20)
    private Integer duration;

    @NotNull
    private String type;

    @ImageNotNull
    private MultipartFile contentPicture;


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenre(String genre) {
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

    public void setContentPicture(MultipartFile contentPicture) {
        this.contentPicture = contentPicture;
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

    public String getGenre() {
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

    public MultipartFile getContentPicture() {
        return contentPicture;
    }


}
