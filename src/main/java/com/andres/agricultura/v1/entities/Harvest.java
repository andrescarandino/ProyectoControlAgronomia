package com.andres.agricultura.v1.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "harvests")
@Getter @Setter @NoArgsConstructor
public class Harvest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalDate date; //cosecha
    @NotNull
    private Double price; //cotizacion
    @NotNull
    private Double coastComercialization;
    @NotNull//%
    private Double performance; //rendimiento qq/ha
    @OneToOne(mappedBy = "harvest")
    @JsonBackReference
    private Campaign campaign;

    public Harvest(LocalDate date, Double price, Double coastComercialization, Double performance, Campaign campaign) {
        this.date = date;
        this.price = price;
        this.coastComercialization = coastComercialization;
        this.performance = performance;
        this.campaign = campaign;
    }
}
