package ar.edu.itba.paw.models;

public class User {

    private Long id, rating;
    private String email, userName, password;
//    //private String password;

    public User(Long id, String email, String userName, String password, Long rating) {
        super();
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }
//    public String getPassword() {
//        return password;
//    }
    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Long getRating() {
        return rating;
    }

    public String getPassword() {
        return password;
    }
}
