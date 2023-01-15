package com.teapot.mrw.billing_ne.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ClientDTO {

    private long id;

    @Size(min = 2, max = 45)
    @NotNull
    @NotEmpty(message = "Name should not be empty")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Size(min = 2, max = 45)
    @NotNull
    @NotEmpty(message = "Surname should not be empty")
    @NotBlank(message = "Surname is mandatory")
    private String surname;

    @Size(min = 6, max = 64)
    @NotNull
    @NotEmpty(message = "Date should not be empty")
    @NotBlank(message = "Date is mandatory")
    private String dateOfBirth;

    @Size(min = 10, max = 255)
    @NotNull
    @NotEmpty(message = "Passport should not be empty")
    @NotBlank(message = "Passport is mandatory")
    private String passportData;

    @Size(min = 2, max = 255)
    @NotNull
    @NotEmpty(message = "Address should not be empty")
    @NotBlank(message = "Address is mandatory")
    private String address;

    @Size(min = 5, max = 55)
    @NotNull
    @NotEmpty(message = "Email should not be empty")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

}
