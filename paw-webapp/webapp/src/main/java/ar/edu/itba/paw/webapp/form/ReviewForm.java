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

    @Size(min = 4, max = 30)
    @Pattern(regexp	= "[a-zA-Z0-9\\s]+")
    private String userName;

    @Size(min = 10, max = 50)
    @Pattern(regexp	= "([+\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+")
    private String email;

    private long id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
