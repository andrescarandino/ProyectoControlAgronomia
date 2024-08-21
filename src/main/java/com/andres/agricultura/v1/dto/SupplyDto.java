package com.andres.agricultura.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplyDto {

    private Long id;
    @NotBlank (message = "name is required")
    private String name;
    @NotNull (message = "priceHectare is required")
    private Double priceHectare;
}
