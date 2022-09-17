package ar.edu.itba.paw.models;

public class User {

    private Long id, reputation;
    private String email, userName, password;

    private byte[] image;
//    //private String password;

    public User(Long id, String email, String userName, String password, Long reputation, byte[] image) {
        super();
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.reputation = reputation;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Long getReputation() {
        return reputation;
    }

    public String getPassword() {
        return password;
    }

    public byte[] getImage(){ return image;}

}
