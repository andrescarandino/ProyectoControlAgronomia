package com.andres.agricultura.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HarvestDto {

    private Long id;
    @NotNull
    private LocalDate date; //cosecha
    @NotNull
    private Double price; //cotizacion
    @NotNull
    private Double coastComercialization;
    @NotNull
    private Double performance; //rendimiento qq/ha
    @NotNull
    private CampaignDto campaing;
}
