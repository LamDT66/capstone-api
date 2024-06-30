package com.fpt.validation.form.user;
import com.fpt.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UserIDExistsValidator implements ConstraintValidator<UserIDExists, Long>{

    @Autowired
    private UserService service;

    @Override
    public boolean isValid(Long data, ConstraintValidatorContext constraintValidatorContext) {

        if (data<=0){
            return false;
        }
        return service.isUserExistsByID(data);
    }
}
