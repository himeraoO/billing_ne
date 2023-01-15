package com.teapot.mrw.billing_ne.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "client")
@EqualsAndHashCode(exclude = "contractList")
@ToString(exclude = "contractList")
public class Client {
    @Id
    @NotNull
    @Column(name = "id", nullable = false, length = 255)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 2, max = 45)
    @NotNull
    @NotEmpty(message = "Name should not be empty")
    @NotBlank(message = "Name is mandatory")
    @Column(name = "name", length = 45)
    private String name;

    @Size(min = 2, max = 45)
    @NotNull
    @NotEmpty(message = "Surname should not be empty")
    @NotBlank(message = "Surname is mandatory")
    @Column(name = "surname", length = 45)
    private String surname;

    @Size(min = 6, max = 64)
    @NotNull
    @NotEmpty(message = "Date should not be empty")
    @NotBlank(message = "Date is mandatory")
    @Column(name = "date_of_birth", length = 64)
    private String dateOfBirth;

    @Size(min = 10, max = 255)
    @NotNull
    @NotEmpty(message = "Passport should not be empty")
    @NotBlank(message = "Passport is mandatory")
    @Column(name = "passport_data", length = 255)
    private String passportData;

    @Size(min = 2, max = 255)
    @NotNull
    @NotEmpty(message = "Address should not be empty")
    @NotBlank(message = "Address is mandatory")
    @Column(name = "address", length = 255)
    private String address;

    @Size(min = 5, max = 55)
    @NotNull
    @NotEmpty(message = "Email should not be empty")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(name = "email", length = 55)
    private String email;

    @Size(min = 8, max = 255)
    @NotNull
    @NotEmpty(message = "Password should not be empty")
    @NotBlank(message = "Password is mandatory")
    @Column(name = "password", length = 255)
    private String password;

    @NotNull
    @Column(name = "role", columnDefinition = "varchar(100) default 'USER'", length = 100)
    private String role = "ROLE_USER";

    @OneToMany(mappedBy= "client", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Contract> contractList = new ArrayList<>();

}
