package com.teapot.mrw.billing_ne.validator;

import com.teapot.mrw.billing_ne.dto.PasswordDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator  implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordDTO passwordDTO = (PasswordDTO) target;

        if (!passwordDTO.isValidPassword()){
            errors.rejectValue("passwordRepetition", "", "Пароли не совпадают");
            errors.rejectValue("password", "", "Пароли не совпадают");
        }
    }
}
