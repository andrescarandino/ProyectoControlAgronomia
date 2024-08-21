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

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationDto {

    private Long id;
//    @NotEmpty (message = "products list must be not empty")
    private Set<ProductDto> products;
    @NotBlank (message = "activity is required")
    private String activity;
    @NotNull (message = "date is required")
    private LocalDate date;
    private String observation;

}
