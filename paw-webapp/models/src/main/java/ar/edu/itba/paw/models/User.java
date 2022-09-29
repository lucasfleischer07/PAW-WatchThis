package ar.edu.itba.paw.models;

public class User {

    private Long id, reputation;
    private String email, userName, password,role;

    private byte[] image;

    public User(Long id, String email, String userName, String password, Long reputation, byte[] image, String role) {
        super();
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.reputation = reputation;
        this.image = image;
        this.role=role;
    }

    public String getRole() {
        return role;
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
