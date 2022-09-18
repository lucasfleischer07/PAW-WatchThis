package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ReviewForm {
    @Size(min = 6, max = 50)
    @Pattern(regexp	= "[a-zA-Z0-9!,.:;=+\n\\-_()?<>$%&#@{}\\[\\]|*\"'~/`^\\s]+")
    private String name;

    @Size(min = 20, max = 500)
    @Pattern(regexp	= "[a-zA-Z0-9!,.:;=+\n\\-_()?<>$%&#@{}\\[\\]|*\"'~/`^\\s]+")
    private String description;

    private long id;
    private Integer rating;
    private String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
