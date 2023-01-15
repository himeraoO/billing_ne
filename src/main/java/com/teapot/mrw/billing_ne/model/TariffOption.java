package com.teapot.mrw.billing_ne.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tariff_option")
@EqualsAndHashCode()
@ToString()
public class TariffOption {
    @Id
    @NotNull
    @Column(name = "id", nullable = false, length = 255)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 1, max = 45)
    @NotNull
    @NotEmpty(message = "Name should not be empty")
    @NotBlank(message = "Name is mandatory")
    @Column(name = "name", length = 45)
    private String name;

    @NotNull
//    @NotEmpty(message = "Price should not be empty")
    @Column(name = "price", precision=10, scale=2)
    @Min(value = 0, message = "Не может быть меньше 0")
    private BigDecimal price;

    @NotNull
//    @NotEmpty(message = "Connection_cost should not be empty")
    @Column(name = "connection_cost", precision=10, scale=2)
    @Min(value = 0, message = "Не может быть меньше 0")
    private BigDecimal connectionCost;
}
