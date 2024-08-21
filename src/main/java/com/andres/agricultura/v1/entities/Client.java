package com.andres.agricultura.v1.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Getter @Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @OneToMany (mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Parcel> parcels;

    public Client() {
        parcels = new HashSet<>();
    }
    public Client(String name) {
        this();
        this.name = name;
    }

    public void addParcel(Parcel parcel){
        this.parcels.add(parcel);
        parcel.setClient(this);
    }

    public void removeParcel(Parcel parcel){
        this.parcels.remove(parcel);
        parcel.setClient(null);
    }


}
