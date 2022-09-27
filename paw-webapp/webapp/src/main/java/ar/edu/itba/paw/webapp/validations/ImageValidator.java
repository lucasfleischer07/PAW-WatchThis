package ar.edu.itba.paw.webapp.validations;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;

public class ImageValidator implements ConstraintValidator<ImageNotNull, MultipartFile> {


    @Override
    public void initialize(ImageNotNull imageNotNull) {
        
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if(multipartFile.getBytes().length>0)
                return true;
        } catch (IOException e) {
            return false;
        }
        return false;
    }
}
