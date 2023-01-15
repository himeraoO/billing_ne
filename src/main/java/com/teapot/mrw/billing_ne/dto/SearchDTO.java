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
public class SearchDTO {

    @Size(min = 1, max = 45)
    @NotNull
    @NotEmpty(message = "Number should not be empty, min = 1, max = 45 symbols")
    @NotBlank(message = "Number is mandatory, min = 1, max = 45 symbols")
    private String number;

}
