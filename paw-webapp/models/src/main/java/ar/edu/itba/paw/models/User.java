package ar.edu.itba.paw.models;

public class User {

    private Long id;
    private String email;
    private String userName;
//    //private String password;

    public User(Long id, String email, String userName){
        super();
        this.id = id;
        this.email = email;
        this.userName = userName;
//        this.password = password;
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
}
