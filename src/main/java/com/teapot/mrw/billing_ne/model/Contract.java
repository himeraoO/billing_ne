package com.teapot.mrw.billing_ne.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "contract")
@EqualsAndHashCode()
@ToString()
public class Contract {
    @Id
    @NotNull
    @Column(name = "id", nullable = false, length = 255)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3, max = 45)
    @NotNull
    @NotEmpty(message = "Number should not be empty")
    @NotBlank(message = "Number is mandatory")
    @Column(name = "number", length = 45)
    private String number;

    @NotNull
    @Column(name = "active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name="tariff_id")
    private Tariff tariff;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ManyToMany
    private List<TariffOption> tariffOptionList = new ArrayList<>();

    public void addOption(TariffOption tariffOption){
        tariffOptionList.add(tariffOption);
    }

    public void removeOption(TariffOption tariffOption){
        tariffOptionList.remove(tariffOption);
    }
}
