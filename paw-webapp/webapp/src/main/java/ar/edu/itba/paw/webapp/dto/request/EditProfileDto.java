package ar.edu.itba.paw.webapp.dto.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditProfileDto {
//    TODO: METER LOS MENSAJES DE ERROR EN CASO DE QUE ESTE VACIO

    @Size(min = 6, max = 50, message = "...")
    @Pattern(regexp	= "([a-zA-Z0-9ñáéíóú!,.:;=+\n\\-_()?<>$%&#@{}\\[\\]|*\"'~/`^\\s]+)?", message = "...")
    private String password;

    @Size(min = 6, max = 50, message = "...")
    @Pattern(regexp	= "([a-zA-Z0-9ñáéíóú!,.:;=+\n\\-_()?<>$%&#@{}\\[\\]|*\"'~/`^\\s]+)?", message = "...")
    private String confirmPassword;

    @Size(min = 6, max = 50, message = "...")
    @Pattern(regexp	= "([a-zA-Z0-9ñáéíóú!,.:;=+\n\\-_()?<>$%&#@{}\\[\\]|*\"'~/`^\\s]+)?", message = "...")
    private String currentPassword;

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

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
