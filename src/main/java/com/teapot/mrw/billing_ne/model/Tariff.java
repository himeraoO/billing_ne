package com.teapot.mrw.billing_ne.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tariff")
@EqualsAndHashCode()
@ToString()
public class Tariff {
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
    @Column(name = "active")
    private boolean active;

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ManyToMany()
    private List<TariffOption> tariffOptionList = new ArrayList<>();

    public void addOption(TariffOption tariffOption){
        tariffOptionList.add(tariffOption);
    }

    public void removeOption(TariffOption tariffOption){
        tariffOptionList.remove(tariffOption);
    }
}
