package com.andres.agricultura.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FertilizerDto {
    private Long id;
    @NotBlank(message = "name is required")
    private String name;
    @NotNull(message = "Dose is required")
    private Double dose; //dosis
    @NotNull(message = "priceHectare is required")
    private Double priceHectare;
    private Double nitrogen;
    private Double phosphorus;
    private Double sulphur;
    private Double zinc;
    @NotNull(message = "Date is required")
    private LocalDate date;
    private String observation;
}
