package com.andres.agricultura.v1.dto;

import com.andres.agricultura.v1.entities.Parcel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDto {
    private Long id;
    @NotBlank (message = "name is required")
    private String name;
    private Set<ParcelDto> parcels;
}
