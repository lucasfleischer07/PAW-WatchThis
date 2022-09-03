package ar.edu.itba.paw.models;

public class Review {
    String name, description, userName, email;
    long id;

    public Review(String name, String description, String userName, String email,long id) {
        this.name = name;
        this.description = description;
        this.userName = userName;
        this.email = email;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(long id){
        this.id = id;
    }
}
