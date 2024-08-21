package com.andres.agricultura.v1.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
@Table(name = "aplications")
@Getter @Setter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy = "applications")
    private Set<Product> products;
    @NotNull
    private String activity;
    @NotNull
    private LocalDate date;
    private String observation;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "applications_campaings",
        joinColumns = @JoinColumn(name = "application_id"),
        inverseJoinColumns = @JoinColumn(name = "campaing_id")
    )
    @JsonIgnore
    private Set<Campaign> campaigns;


    public Application() {
        products = new HashSet<>();
        campaigns = new HashSet<>();

    }


    public Application( String activity, LocalDate date, String observation) {
        this();
        this.activity = activity;
        this.date = date;
        this.observation = observation;

    }

    public Double calculateTotalPriceProducts(){
        return this.getProducts().stream()
                .mapToDouble(Product::getPriceHectare)
                .sum();
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.getApplications().add(this);
    }
 
    public void removeProduct(Product product) {
        this.products.remove(product);
        product.getApplications().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(id, that.id) && Objects.equals(activity, that.activity) && Objects.equals(date, that.date) && Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activity, date, observation);
    }
}
