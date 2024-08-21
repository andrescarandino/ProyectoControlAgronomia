package com.andres.agricultura.v1.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parcels")
@Getter @Setter
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    private Double surface;
    private String usability;
    private Integer productivityIndex;
    private String nomeclature;
    private String limitation;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "parcels_campaings",
        joinColumns = @JoinColumn(name = "parcel_id"),
        inverseJoinColumns = @JoinColumn(name = "campaing_id")
    )
    private Set<Campaign> campaigns;
    @ManyToOne
    @JoinColumn(name = "client_id")
    //@JsonManagedReference
    @JsonBackReference
    private Client client;

    public Parcel() {
        campaigns = new HashSet<>();
    }

    public Parcel(String name, Double surface, String usability, Integer productivityIndex, String nomeclature,
            String limitation) {
        this();
        this.name = name;
        this.surface = surface;
        this.usability = usability;
        this.productivityIndex = productivityIndex;
        this.nomeclature = nomeclature;
        this.limitation = limitation;
    }

    public void addCampaign(Campaign campaign){
        this.campaigns.add(campaign);
        campaign.getParcels().add(this);
    }

    public void removeCampaign(Campaign campaign){
        this.campaigns.remove(campaign);
        campaign.getParcels().remove(this);
    }




    
}
