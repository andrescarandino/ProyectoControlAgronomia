package com.andres.agricultura.v1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@Entity
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Double priceHectare;

    @ManyToMany
    @JoinTable(
            name = "supplies_campaings",
            joinColumns = @JoinColumn(name = "supply_id"),
            inverseJoinColumns = @JoinColumn(name = "campaing_id")
    )
    @JsonIgnore
    private Set<Campaign> campaigns;

    public Supply() {
        this.campaigns = new HashSet<>();
    }

    public Supply(String name, Double priceHectare) {
        this();
        this.id = id;
        this.name = name;
        this.campaigns = campaigns;
        this.priceHectare = priceHectare;
    }
}
