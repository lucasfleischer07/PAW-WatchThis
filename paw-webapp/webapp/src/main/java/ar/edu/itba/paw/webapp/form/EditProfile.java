package ar.edu.itba.paw.webapp.form;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditProfile {

    @Size(min = 6, max = 50)
    @Pattern(regexp	= "[a-zA-Z0-9!,.:;=+\n\\-_()?<>$%&#@{}\\[\\]|*\"'~/`^\\s]+")
    private String password;

    private MultipartFile profilePicture;

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}