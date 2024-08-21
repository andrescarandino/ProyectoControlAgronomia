package com.andres.agricultura.v1.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "campaigns")
@Getter @Setter
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name; //EJ: 21-22
    @NotNull
    private String typeCorp; //cultivo
    @NotNull
    private String hibryd;
    @NotNull
    private LocalDate sowingDate;   //siembra
    private Double density;
    private Double spaceFurrow; //surco
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "harvest_id", referencedColumnName = "id")
    @JsonManagedReference
    private Harvest harvest;
    @ManyToMany(mappedBy = "campaigns")
    private Set<Supply> supplies;
    @ManyToMany (mappedBy = "campaigns")
    private Set<Work> works;
    @ManyToMany(mappedBy = "campaigns")
    private Set<Fertilizer> fertilizers;
    @ManyToMany(mappedBy = "campaigns")
    private Set<Application> applications;
    @ManyToMany(mappedBy = "campaigns")
    @JsonIgnore
    private Set<Parcel> parcels;



    public Campaign() {
        works = new HashSet<>();
        fertilizers = new HashSet<>();
        applications = new HashSet<>();
        parcels = new HashSet<>();
        supplies = new HashSet<>();
    }

    public Campaign(String name, String typeCorp, String hibryd, LocalDate sowingDate,
                    Double density, Double spaceFurrow, Harvest harvest) {
        this();
        this.name = name;
        this.typeCorp = typeCorp;
        this.hibryd = hibryd;
        this.sowingDate = sowingDate;
        this.density = density;
        this.spaceFurrow = spaceFurrow;
        this.harvest = harvest;
    }

    public void addApplication(Application application) {
        this.applications.add(application);
        application.getCampaigns().add(this);
    }

    public void removeApplication(Application application) {
        this.applications.remove(application);
        application.getCampaigns().remove(this);
    }

    public void addFertilizer(Fertilizer fertilizer){
        this.fertilizers.add(fertilizer);
        fertilizer.getCampaigns().add(this);
    }

    public void removeFertilizer(Fertilizer fertilizer){
        this.fertilizers.remove(fertilizer);
        fertilizer.getCampaigns().remove(this);
    }

    public void addWork(Work work){
        this.works.add(work);
        work.getCampaigns().add(this);
    }

    public void removeWork(Work work){
        this.works.remove(work);
        work.getCampaigns().remove(this);
    }

    public void removeParcel(Parcel parcel){
        this.parcels.remove(parcel);
        parcel.getCampaigns().remove(this);
    }

    public void addSupply(Supply supply){
        this.supplies.add(supply);
        supply.getCampaigns().add(this);
    }

    public void removeSupply(Supply supply){
        this.supplies.remove(supply);
        supply.getCampaigns().remove(this);
    }



}
