package com.andres.agricultura.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParcelDto {

    private Long id;
    @NotBlank(message = "name is required")
    private String name;
    private Double surface;
    private String usability;
    private Integer productivityIndex;
    private String nomeclature;
    private String limitation;
    private Set<CampaignDto> campaigns;

}
