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
@Table(name = "works")
@Getter @Setter
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Double priceHectare;
    @NotNull
    private LocalDate date;
    private String observation;
    @ManyToMany
    @JoinTable(
        name = "works_campaings",
        joinColumns = @JoinColumn(name = "work_id"),
        inverseJoinColumns = @JoinColumn(name = "campaing_id")
    )
    @JsonIgnore
    private Set<Campaign> campaigns;

    public Work(){
        campaigns = new HashSet<>();
    }
   
    public Work(String name, Double priceHectare, LocalDate date, String observation) {
        this();
        this.name = name;
        this.priceHectare = priceHectare;
        this.date = date;
        this.observation = observation;
    }


    

    
}
