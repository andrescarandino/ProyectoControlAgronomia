package com.andres.agricultura.v1.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fertilizers")
@Getter @Setter
public class Fertilizer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Double dose; //dosis
    @NotNull
    private Double priceHectare;
    private Double nitrogen;
    private Double phosphorus;
    private Double sulphur;
    private Double zinc;
    @NotNull
    private LocalDate date;
    private String observation;
    @ManyToMany
    @JoinTable(
        name = "fertilizers_campaings",
        joinColumns = @JoinColumn(name = "fertilizer_id"),
        inverseJoinColumns = @JoinColumn(name = "campaing_id")
    )
    @JsonIgnore
    private Set<Campaign> campaigns;
    
    public Fertilizer() {
        campaigns = new HashSet<>();
    }

    public Fertilizer(String name, Double dose, Double priceHectare, Double nitrogen, Double phosphorus, Double sulphur,
            Double zinc, LocalDate date, String observation) {
        this();
        this.name = name;
        this.dose = dose;
        this.priceHectare = priceHectare;
        this.nitrogen = nitrogen;
        this.phosphorus = phosphorus;
        this.sulphur = sulphur;
        this.zinc = zinc;
        this.date = date;
        this.observation = observation;
    }

}
