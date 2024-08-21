package com.andres.agricultura.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties( ignoreUnknown = true)
public class CampaignDto {

    private Long id;
    @NotBlank (message = "name is required")
    private String name; //EJ: 21-22
    @NotBlank (message = "typeCorp is required")
    private String typeCorp; //cultivo
    @NotBlank (message = "hybrid is required")
    private String hibryd;
    @NotNull
    private LocalDate sowingDate;   //siembra
    private Double density;
    private Double spaceFurrow; //surco
    private Set<WorkDto> Works;
    private Set<FertilizerDto> fertilizers;
    private Set<ApplicationDto> applications;
    private Set<SupplyDto> supplies;
    private HarvestDto harvest;

}
