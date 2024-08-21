package com.andres.agricultura.v1.entities;

import java.util.HashSet;
import java.util.Objects;
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
@Table(name = "products")
@Getter @Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Double dose;
    @NotNull
    private Double priceHectare;

    @ManyToMany
    @JoinTable(
        name = "product_application",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "application_id")
    )
    @JsonIgnore
    private Set<Application> applications;
    
    public Product() {
        applications = new HashSet<>();
    }

    public Product(String name, Double dose, Double priceHectare) {
        this();
        this.name = name;
        this.dose = dose;
        this.priceHectare = priceHectare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(dose, product.dose) && Objects.equals(priceHectare, product.priceHectare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dose, priceHectare);
    }
}
