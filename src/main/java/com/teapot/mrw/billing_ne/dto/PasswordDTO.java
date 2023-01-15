package com.teapot.mrw.billing_ne.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PasswordDTO {

    @Size(min = 8, max = 255)
    @NotNull
    @NotEmpty(message = "Password should not be empty")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Size(min = 8, max = 255)
    @NotNull
    @NotEmpty(message = "Password should not be empty")
    @NotBlank(message = "Password is mandatory")
    private String passwordRepetition;

    public boolean isValidPassword(){
        return password.equals(passwordRepetition);
    }
}
